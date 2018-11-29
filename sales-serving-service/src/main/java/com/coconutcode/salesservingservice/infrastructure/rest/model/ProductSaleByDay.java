package com.coconutcode.salesservingservice.infrastructure.rest.model;

import lombok.Data;

@Data
public class ProductSaleByDay {
    private String topic;
    private String day;
    private long units;
}
