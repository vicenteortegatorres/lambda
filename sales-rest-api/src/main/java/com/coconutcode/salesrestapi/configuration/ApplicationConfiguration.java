package com.coconutcode.salesrestapi.configuration;

import com.coconutcode.salesrestapi.infrastructure.persistence.SaleRepository;
import com.coconutcode.salesrestapi.service.GetStoredSales;
import com.coconutcode.salesrestapi.service.SaleService;
import com.coconutcode.salesrestapi.service.SaleStorer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ApplicationConfiguration {
    @Value("${store.filename:}")
    private String fileName;

    @Bean
    public SaleRepository createSaleRepository() {
        return new SaleRepository(new File(fileName));
    }

    @Bean
    public SaleService createSaleService(SaleStorer saleStorer, GetStoredSales getStoredSales) {
        return new SaleService(saleStorer, getStoredSales);
    }
}
