package com.coconutcode.salesbatchservice.persistence.mondodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDayViewRepository extends MongoRepository<ProductDayView, String> {

}
