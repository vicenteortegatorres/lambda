package com.coconutcode.salesservingservice.infrastructure.rest;

import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;

import java.util.List;

public interface GetProductsSaleByDay {
    List<ProductSaleByDay> getProductsSaleByDay();
}
