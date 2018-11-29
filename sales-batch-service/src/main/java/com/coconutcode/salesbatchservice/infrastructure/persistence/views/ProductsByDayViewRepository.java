package com.coconutcode.salesbatchservice.infrastructure.persistence.views;

import com.coconutcode.salesbatchservice.infrastructure.persistence.views.model.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsByDayViewRepository extends MongoRepository<ProductView, String>{

}
