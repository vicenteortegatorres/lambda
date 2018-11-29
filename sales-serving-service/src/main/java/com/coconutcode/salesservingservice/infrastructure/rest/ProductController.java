package com.coconutcode.salesservingservice.infrastructure.rest;

import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController("/products")
public class ProductController {
    @Autowired
    private GetProductsSaleByDay getProductsSaleByDay;

    @GetMapping
    public List<ProductSaleByDay> getProductsSaleByDay() {
        return getProductsSaleByDay.getProductsSaleByDay();
    }
}
