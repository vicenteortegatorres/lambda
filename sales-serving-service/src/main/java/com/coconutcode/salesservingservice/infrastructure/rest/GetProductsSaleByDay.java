package com.coconutcode.salesservingservice.infrastructure.rest;

import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;

import java.util.Optional;

public interface GetProductsSaleByDay {
    Optional<ProductSaleByDay> getProductsSaleByDay(String productId, String day);
}
