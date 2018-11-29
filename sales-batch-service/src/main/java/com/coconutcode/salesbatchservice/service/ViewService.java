package com.coconutcode.salesbatchservice.service;

import com.coconutcode.infrastructure.persistence.model.Sale;
import com.coconutcode.salesbatchservice.infrastructure.persistence.sales.SaleRepository;
import com.coconutcode.salesbatchservice.infrastructure.persistence.views.ProductsByDayViewRepository;
import com.coconutcode.salesbatchservice.infrastructure.persistence.views.model.ProductView;
import com.coconutcode.salesbatchservice.infrastructure.persistence.views.model.ViewType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ViewService {
    private static final String APP_NAME = "SalesBatchService";

    private static final String LOCAL = "local";

    @Autowired
    private GetSalesEvents getSalesEvents;

    @Autowired
    private ProductsByDayViewRepository productsByDayViewRepository;

    @Autowired
    private SaleRepository saleRepository;

    public void generate() {
        try {
            val perJavaRDD = getSaleJavaRDD();
            createProductsByDayViews(perJavaRDD);
            createCategoriesByDayViews(perJavaRDD);
        } catch (IOException e) {
            log.error("Error creating batch views: ", e);
        }
    }

    private void createCategoriesByDayViews(JavaRDD<Sale> perJavaRDD) {
        val nCategoties = countBy(perJavaRDD, sale -> sale.getProductCategory().toString());
        productsByDayViewRepository.saveAll(map(nCategoties, ViewType.CATEGORY));
    }

    private void createProductsByDayViews(JavaRDD<Sale> perJavaRDD) {
        val nProduct = countBy(perJavaRDD, sale -> sale.getProductId().toString());
        productsByDayViewRepository.saveAll(map(nProduct, ViewType.PRODUCT));
    }

    private JavaRDD<Sale> getSaleJavaRDD() throws IOException {
        val sparkConf = new SparkConf().setMaster(LOCAL).setAppName(APP_NAME);
        val sc = new JavaSparkContext(sparkConf);
        val sales = saleRepository.getSales();
        return sc.parallelize(sales);
    }

    private List<Tuple2<ProductView, Integer>> countBy(JavaRDD<Sale> perJavaRDD, Function<Sale, String> function0) {
        val ones = perJavaRDD.mapToPair((PairFunction<Sale, ProductView, Integer>)
                s -> new Tuple2<>(new ProductView(function0.call(s), s.getSaleDate()), 1));
        val counts = ones.reduceByKey((Function2<Integer, Integer, Integer>)
                (i1, i2) -> i1 + i2);
        return counts.collect();
    }

    private List<ProductView> map(List<Tuple2<ProductView, Integer>> nProduct, ViewType viewType) {
        return nProduct.stream().map(tuple -> {
            val productsByDayView = tuple._1;
            productsByDayView.setUnits(tuple._2);
            productsByDayView.setViewType(viewType);
            return productsByDayView;
        }).collect(Collectors.toList());
    }
}
