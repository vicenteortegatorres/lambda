package com.coconutcode.salesrestapi;

import com.coconutcode.salesrestapi.serialization.model.ProductCategory;
import com.coconutcode.salesrestapi.serialization.model.Sale;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesRestApiApplicationTests {

    public static final String FILENAME = "salesEvents.txt";

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

		serialize(Arrays.asList(sale, sale1, sale));

        deserialize();
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

    private void serialize(List<Sale> sales) {
		DatumWriter<Sale> writer = new SpecificDatumWriter<>(Sale.class);
		try {
			val fop = new FileOutputStream(new File(FILENAME));
			val jsonEncoder = getSaleEncoder(fop);
            for (val sale : sales) {
                writer.write(sale, jsonEncoder);
            }
            jsonEncoder.flush();
			fop.flush();
			fop.close();
		} catch (IOException e) {
			log.error("Serialization error:" + e.getMessage());
		}
	}

    private void deserialize() {
        BufferedReader br = null;
        FileReader fr = null;

        try {

            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                DatumReader<Sale> reader
                        = new SpecificDatumReader<>(Sale.class);
                Decoder decoder = null;

                    decoder = DecoderFactory.get().jsonDecoder(
                            Sale.getClassSchema(), new String(sCurrentLine));
                    Sale a = reader.read(null, decoder);
                    System.out.println(a);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }

    }

    private JsonEncoder getSaleEncoder(FileOutputStream fop) throws IOException {
        return EncoderFactory.get().jsonEncoder(Sale.getClassSchema(), fop);
    }


}
