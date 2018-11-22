package com.coconutcode.salesrestapi.infrastructure.rest;

import com.coconutcode.salesrestapi.entity.Sale;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleRestController {
    @Autowired
    private CreateSale createSaleUserCase;

    @Autowired
    private GetSales getSales;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Sale sale) {
        try {
            createSaleUserCase.createSale(sale);
            return createOkResponse(sale);
        } catch (Exception exception){
            return createBadRequestResponse(exception);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            val sales = getSales.getAll();
            return createOkResponse(sales);
        } catch (Exception exception){
            return createBadRequestResponse(exception);
        }
    }

    private ResponseEntity<?> createOkResponse(List<Sale> sales) {
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    private ResponseEntity<String> createBadRequestResponse(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Sale> createOkResponse(@RequestBody Sale sale) {
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }
}
