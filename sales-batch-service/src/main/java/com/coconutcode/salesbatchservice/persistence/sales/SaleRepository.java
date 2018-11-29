package com.coconutcode.salesbatchservice.persistence.sales;

import com.coconutcode.infrastructure.persistence.model.Sale;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class SaleRepository {

    @Value("${store.filename:}")
    private String fileName;

    public List<Sale> getSales() throws IOException {
        return deserialize(new File(fileName));
    }

    private List<Sale> deserialize(File file) throws IOException {
        return StreamSupport.stream(getSaleDataFileReader(file).spliterator(), false)
                .collect(Collectors.toList());
    }

    private DataFileReader<Sale> getSaleDataFileReader(File file) throws IOException {
        return new DataFileReader<>(file, new SpecificDatumReader<>(Sale.class));
    }
}
