package com.coconutcode.salesservingservice.infrastructure.persistence;

import com.coconutcode.salesservingservice.infrastructure.persistence.model.CompositeKey;
import com.coconutcode.salesservingservice.infrastructure.persistence.model.ProductView;
import com.coconutcode.salesservingservice.infrastructure.persistence.model.ViewType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductsByDayViewRepository extends MongoRepository<ProductView, CompositeKey>{
    Optional<ProductView> findByIdAndViewType(CompositeKey compositeKey, ViewType viewTypea);
}
