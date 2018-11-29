package com.coconutcode.salesbatchservice.persistence.views;

import com.coconutcode.salesbatchservice.persistence.views.model.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsByDayViewRepository extends MongoRepository<ProductView, String> {

}
