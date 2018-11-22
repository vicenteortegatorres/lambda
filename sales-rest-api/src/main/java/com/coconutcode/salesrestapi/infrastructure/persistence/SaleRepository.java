package com.coconutcode.salesrestapi.infrastructure.persistence;

import com.coconutcode.salesrestapi.service.GetStoredSales;
import com.coconutcode.salesrestapi.service.SaleStorer;
import com.coconutcode.salesrestapi.infrastructure.persistence.model.Sale;
import com.coconutcode.salesrestapi.infrastructure.persistence.model.ProductCategory;
import lombok.val;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SaleRepository implements SaleStorer, GetStoredSales {
    private final File saleStoredFile;

    public SaleRepository(File saleStoredFile) {
        this.saleStoredFile = saleStoredFile;
    }

    @Override
    public void save(com.coconutcode.salesrestapi.entity.Sale sale) {
        try {
            serialize(mapSale(sale), saleStoredFile);
        } catch (IOException e) {
            throw new StoredException("Error storing sale fact", e);
        }
    }

    @Override
    public List<com.coconutcode.salesrestapi.entity.Sale> getAll() {
        try {
            return deserialize(saleStoredFile).stream().map(this::map).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StoredException("Error getting stored sale facts", e);
        }
    }

    private com.coconutcode.salesrestapi.entity.Sale map(Sale s) {
        val sale = new com.coconutcode.salesrestapi.entity.Sale();
        sale.setProductCategory(
                com.coconutcode.salesrestapi.entity.ProductCategory.valueOf(s.getProductCategory().name()));
        sale.setProductId(s.getProductId().toString());
        sale.setSaleDate(s.getSaleDate());
        return sale;
    }

    private Sale mapSale(com.coconutcode.salesrestapi.entity.Sale sale) {
        val persistenceSale = new Sale();
        persistenceSale.setProductCategory(ProductCategory.valueOf(sale.getProductCategory().name()));
        persistenceSale.setProductId(sale.getProductId());
        persistenceSale.setSaleDate(sale.getSaleDate());
        return persistenceSale;
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
        return StreamSupport.stream( getSaleDataFileReader(file).spliterator(), false)
                .collect(Collectors.toList());
    }

    private DataFileReader<Sale> getSaleDataFileReader(File file) throws IOException {
        return new DataFileReader<>(file, new SpecificDatumReader<>(Sale.class));
    }
}
