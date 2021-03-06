package com.coconutcode.salesservingservice.infrastructure.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductSaleByDay {
    private String productId;
    private String day;
    private long units;
}
