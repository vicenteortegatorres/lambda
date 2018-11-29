package com.coconutcode.salesbatchservice.service;

import com.coconutcode.infrastructure.persistence.model.Sale;

import java.io.IOException;
import java.util.List;

public interface GetSalesEvents {
    List<Sale> getSales() throws IOException;
}
