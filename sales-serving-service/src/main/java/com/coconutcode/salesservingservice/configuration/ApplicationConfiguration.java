package com.coconutcode.salesservingservice.configuration;

import com.coconutcode.salesservingservice.infrastructure.persistence.ProductsByDayViewRepository;
import com.coconutcode.salesservingservice.service.ProductViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Autowired
    private ProductsByDayViewRepository productsByDayViewRepository;

    @Bean
    public ProductViewService createProductViewService() {
        return new ProductViewService(productsByDayViewRepository);
    }
}
