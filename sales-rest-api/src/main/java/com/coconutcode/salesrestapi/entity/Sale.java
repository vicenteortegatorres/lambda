package com.coconutcode.salesrestapi.entity;

import lombok.Data;

@Data
public class Sale {
    private String productId;
    private ProductCategory productCategory;
    private Long saleDate;
}
