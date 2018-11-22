package com.coconutcode.salesrestapi.service;

import com.coconutcode.salesrestapi.entity.Sale;

public interface SaleStorer {
    void save(Sale sale);
}
