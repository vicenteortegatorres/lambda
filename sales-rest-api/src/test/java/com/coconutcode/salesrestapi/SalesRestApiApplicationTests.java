package com.coconutcode.salesrestapi;

import com.coconutcode.salesrestapi.infrastructure.persistence.model.ProductCategory;
import com.coconutcode.salesrestapi.infrastructure.persistence.model.Sale;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesRestApiApplicationTests {

    private static final String FILENAME = "salesEvents.txt";

    @Test
	public void contextLoads() {
		val sale = new Sale();
		sale.setProductCategory(ProductCategory.SPORT);
		sale.setSaleDate(System.currentTimeMillis());
		sale.setProductId("product1");

        val sale1 = new Sale();
		sale1.setProductCategory(ProductCategory.COOKING);
		sale1.setSaleDate(System.currentTimeMillis());
        sale1.setProductId("23423");

        val file = new File(FILENAME);
        try {
            for (Sale s : Arrays.asList(sale, sale1, sale)) {
                serialize(s, file);
            }

            val sales = deserialize(file);

            sales.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Error ", e);
        }
    }

    private String generateSchema() {
        return SchemaBuilder.record("Sale")
                .namespace("com.coconutcode.salesrestapi.serialization.entity")
                .fields().requiredString("productId")
                .name("productCategory")
                .type()
                .enumeration("ProductCategory")
                .symbols("GARDEN", "KITCHEN", "COOKING", "FOOD", "DRINK", "SPORT", "FASHION", "FURNITURE")
                .noDefault()
                .requiredLong("saleDate")
                .endRecord().toString();
    }

    private void serialize(Sale sale, File file) throws IOException {
        val saleDataFileWriter = getSaleDataFileWriter(file);
        saleDataFileWriter.append(sale);
        saleDataFileWriter.close();
	}

    private DataFileWriter<Sale> getSaleDataFileWriter(File file) throws IOException {
        val saleDataFileWriter = new DataFileWriter<Sale>(new SpecificDatumWriter<>(Sale.getClassSchema()));
        createOrAppend(file, saleDataFileWriter);
        return saleDataFileWriter;
    }

    private void createOrAppend(File file, DataFileWriter<Sale> saleDataFileWriter) throws IOException {
        if(file.exists()) {
            saleDataFileWriter.appendTo(file);
        } else {
            saleDataFileWriter.create(Sale.getClassSchema(), file);
        }
    }

    private List<Sale> deserialize(File file) throws IOException {
        return StreamSupport.stream( getSaleDataFileReader(file).spliterator(), false).collect(Collectors.toList());
    }

    private DataFileReader<Sale> getSaleDataFileReader(File file) throws IOException {
        return new DataFileReader<>(file, new SpecificDatumReader<>(Sale.class));
    }
}
