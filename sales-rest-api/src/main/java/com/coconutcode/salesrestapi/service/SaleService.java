package com.coconutcode.salesrestapi.service;

import com.coconutcode.salesrestapi.entity.Sale;
import com.coconutcode.salesrestapi.infrastructure.rest.CreateSale;
import com.coconutcode.salesrestapi.infrastructure.rest.GetSales;

import java.util.List;

public class SaleService implements CreateSale, GetSales {
    private final SaleStorer saleStored;
    private final GetStoredSales getStoredSales;

    public SaleService(SaleStorer userRepository, GetStoredSales getStoredSales) {
        this.saleStored = userRepository;
        this.getStoredSales = getStoredSales;
    }

    @Override
    public void createSale(Sale sale) {
        saleStored.save(sale);
    }

    @Override
    public List<Sale> getAll() {
        return getStoredSales.getAll();
    }
}
