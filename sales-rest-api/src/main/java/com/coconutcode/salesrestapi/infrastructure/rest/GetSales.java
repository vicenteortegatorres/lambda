package com.coconutcode.salesrestapi.infrastructure.rest;

import com.coconutcode.salesrestapi.entity.Sale;

import java.util.List;

public interface GetSales {
    List<Sale> getAll();
}
