package com.coconutcode.salesrestapi;

import com.coconutcode.salesrestapi.serialization.model.ProductCategory;
import com.coconutcode.salesrestapi.serialization.model.Sale;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesRestApiApplicationTests {

    private static final String FILENAME = "salesEvents.txt";

    @Test
	public void contextLoads() {
		Sale sale = new Sale();
		sale.setProductCategory(ProductCategory.SPORT);
		sale.setSaleDate(System.currentTimeMillis());
		sale.setProductId("product1");

        Sale sale1 = new Sale();
		sale1.setProductCategory(ProductCategory.COOKING);
		sale1.setSaleDate(System.currentTimeMillis());
        sale1.setProductId("23423");

        val file = new File(FILENAME);

		serialize(Arrays.asList(sale, sale1, sale), file);

        val sales = deserialize(file);

        sales.forEach(System.out::println);
    }

    private String generateSchema() {
        return SchemaBuilder.record("Sale")
                .namespace("com.coconutcode.salesrestapi.serialization.model")
                .fields().requiredString("productId")
                .name("productCategory")
                .type()
                .enumeration("ProductCategory")
                .symbols("GARDEN", "KITCHEN", "COOKING", "FOOD", "DRINK", "SPORT", "FASHION", "FURNITURE")
                .noDefault()
                .requiredLong("saleDate")
                .endRecord().toString();
    }

    private void serialize(List<Sale> sales, File file) {
        val saleSpecificDatumWriter = new SpecificDatumWriter<Sale>(Sale.class);
        val saleDataFileWriter = new DataFileWriter<Sale>(saleSpecificDatumWriter);
        try {
            saleDataFileWriter.create(Sale.getClassSchema(), file);
            for (val sale : sales) {
                saleDataFileWriter.append(sale);
            }
		} catch (IOException e) {
			log.error("Serialization error:", e);
		} finally {
            try {
                saleDataFileWriter.close();
            } catch (IOException e) {
                log.error("Error closing file",  e);
            }
        }
	}

    private ArrayList<Sale> deserialize(File file) {
        val sales = new ArrayList<Sale>();
        val saleSpecificDatumReader = new SpecificDatumReader<Sale>(Sale.getClassSchema());
        try {
            val saleDataFileReader = new DataFileReader<Sale>(file, saleSpecificDatumReader);
            while (saleDataFileReader.hasNext()) {
                val sale = saleDataFileReader.next();
                sales.add(sale);
            }
        } catch (IOException e) {
            log.error("Error reading file:", e);
        }
        return sales;
    }
}
