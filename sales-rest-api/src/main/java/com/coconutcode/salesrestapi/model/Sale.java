package com.coconutcode.salesrestapi.model;

import lombok.Data;

@Data
public class Sale {
    private String productId;
    private ProductCategory productCategory;
    private Long saleDate;
}
