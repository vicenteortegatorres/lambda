package com.coconutcode.salesservingservice.configuration;

import com.coconutcode.salesservingservice.service.ProductViewService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ProductViewService createProductViewService() {
        return new ProductViewService();
    }
}
