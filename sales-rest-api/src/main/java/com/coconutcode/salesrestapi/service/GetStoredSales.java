package com.coconutcode.salesrestapi.service;

import com.coconutcode.salesrestapi.entity.Sale;

import java.util.List;

public interface GetStoredSales {
    List<Sale> getAll();
}
