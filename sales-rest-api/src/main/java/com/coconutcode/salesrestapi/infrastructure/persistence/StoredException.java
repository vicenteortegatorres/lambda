package com.coconutcode.salesrestapi.infrastructure.persistence;

public class StoredException extends RuntimeException {
    public StoredException(String error, Exception e) {
        super(error, e);
    }
}
