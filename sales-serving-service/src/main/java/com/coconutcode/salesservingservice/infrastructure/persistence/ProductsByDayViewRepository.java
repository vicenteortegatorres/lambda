package com.coconutcode.salesservingservice.infrastructure.persistence;

import com.coconutcode.salesservingservice.infrastructure.persistence.model.ProductView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsByDayViewRepository extends MongoRepository<ProductView, String>{

}
