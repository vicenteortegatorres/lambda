package com.coconutcode.salesbatchservice;

import com.coconutcode.infrastructure.persistence.model.ProductCategory;
import com.coconutcode.infrastructure.persistence.model.Sale;
import com.coconutcode.salesbatchservice.persistence.mondodb.ProductDayView;
import com.coconutcode.salesbatchservice.persistence.mondodb.ProductDayViewRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.spark.SparkConf;
import scala.Tuple2;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesBatchServiceApplicationTests {
    @Autowired
    private ProductDayViewRepository productDayViewRepository;

	@Test
	public void contextLoads() {
		try {
			val sales = deserialize(new File("salesEvents.txt"));
            Sale e = new Sale();
            e.setProductId("dsadas");
            e.setProductCategory(ProductCategory.KITCHEN);
            e.setSaleDate(System.currentTimeMillis());
            sales.add(e);

            Sale e1 = new Sale();
            e1.setProductId("gazpacho");
            e1.setProductCategory(ProductCategory.DRINK);
            e1.setSaleDate(System.currentTimeMillis());
            sales.add(e1);

            Sale e2 = new Sale();
            e2.setProductId("gazpacho2");
            e2.setProductCategory(ProductCategory.DRINK);
            e2.setSaleDate(System.currentTimeMillis());
            sales.add(e2);

			val sparkConf = new SparkConf().setMaster("local").setAppName("SalesBatchService");
			val sc = new JavaSparkContext(sparkConf);
			val perJavaRDD = sc.parallelize(sales);

            val nProduct = countBy(perJavaRDD, sale -> sale.getProductId().toString());
            val nCategoties = countBy(perJavaRDD, sale -> sale.getProductCategory().toString());

			log.info("nProduct " + nProduct);
			log.info("nCategoties " + nCategoties);

            productDayViewRepository.saveAll(nProduct.stream().map(s->s._1).collect(Collectors.toList()));

            productDayViewRepository.findAll();
		} catch (IOException e) {
			log.error("Error creating batch views: ", e);
		}
	}

    private List<Tuple2<ProductDayView, Integer>> countBy(JavaRDD<Sale> perJavaRDD, Function<Sale, String> function0) {
        val ones = perJavaRDD.mapToPair((PairFunction<Sale, ProductDayView, Integer>)
                s -> {
                    val id = function0.call(s);
                    return new Tuple2<>(new ProductDayView(id, s.getSaleDate()), 1);
                });
        val counts = ones.reduceByKey((Function2<Integer, Integer, Integer>)
                (i1, i2) -> i1 + i2);
        return counts.collect();
    }

	private List<Sale> deserialize(File file) throws IOException {
		return StreamSupport.stream(getSaleDataFileReader(file).spliterator(), false)
				.collect(Collectors.toList());
	}

	private DataFileReader<Sale> getSaleDataFileReader(File file) throws IOException {
		return new DataFileReader<>(file, new SpecificDatumReader<>(Sale.class));
	}
}
