package com.coconutcode.salesservingservice.service;

import com.coconutcode.salesservingservice.infrastructure.persistence.ProductsByDayViewRepository;
import com.coconutcode.salesservingservice.infrastructure.persistence.model.CompositeKey;
import com.coconutcode.salesservingservice.infrastructure.persistence.model.ProductView;
import com.coconutcode.salesservingservice.infrastructure.persistence.model.ViewType;
import com.coconutcode.salesservingservice.infrastructure.rest.GetCategoriesByDay;
import com.coconutcode.salesservingservice.infrastructure.rest.GetProductsSaleByDay;
import com.coconutcode.salesservingservice.infrastructure.rest.model.CategoriesByDay;
import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;

import java.util.Optional;

public class ProductViewService implements GetProductsSaleByDay, GetCategoriesByDay {
    final private ProductsByDayViewRepository productsByDayViewRepository;

    public ProductViewService(ProductsByDayViewRepository productsByDayViewRepository) {
        this.productsByDayViewRepository = productsByDayViewRepository;
    }

    @Override
    public Optional<ProductSaleByDay> getProductsSaleByDay(String productId, String day) {
        return productsByDayViewRepository.findByIdAndViewType(new CompositeKey(productId, day),
                ViewType.PRODUCT).flatMap(this::mapToProductSaleByDay);
    }

    @Override
    public Optional<CategoriesByDay> getCategoriesByDay(String category, String day) {
        return productsByDayViewRepository.findByIdAndViewType(new CompositeKey(category, day),
                ViewType.CATEGORY).flatMap(this::mapToCategoriesByDay);
    }

    private Optional<ProductSaleByDay> mapToProductSaleByDay(ProductView ps) {
        return Optional.of(new ProductSaleByDay(ps.getTopic(), ps.getDay(), ps.getUnits()));
    }

    private Optional<CategoriesByDay> mapToCategoriesByDay(ProductView ps) {
        return Optional.of(new CategoriesByDay(ps.getTopic(), ps.getDay(), ps.getUnits()));
    }
}