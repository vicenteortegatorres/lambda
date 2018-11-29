package com.coconutcode.salesservingservice.service;

import com.coconutcode.salesservingservice.infrastructure.rest.GetProductsSaleByDay;
import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;

import java.util.List;

public class ProductViewService implements GetProductsSaleByDay {
    @Override
    public List<ProductSaleByDay> getProductsSaleByDay() {
        return null;
    }
}