package com.coconutcode.salesrestapi.infrastructure.rest;

import com.coconutcode.salesrestapi.entity.Sale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleRestController {
    private static final String SALES_PATH = "/sales";

    @PostMapping(value = SALES_PATH)
    public ResponseEntity<?> create(@RequestBody Sale sale) {
        try {
            //return createOkResponse(userPresenterAdapter.createUser(user));
            return null;
        } catch (Exception exception){
            return createBadRequestResponse(exception);
        }
    }

    private ResponseEntity<String> createBadRequestResponse(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Sale> createOkResponse(@RequestBody Sale sale) {
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

}
