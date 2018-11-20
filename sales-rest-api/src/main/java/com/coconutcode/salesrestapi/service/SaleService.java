package com.coconutcode.salesrestapi.service;

import com.coconutcode.salesrestapi.entity.Sale;

public class SaleService {
    private final StoreSale userRepository;

    public SaleService(StoreSale userRepository) {
        this.userRepository = userRepository;
    }

    void save(Sale sale) {
        userRepository.save(sale);
    }
}
