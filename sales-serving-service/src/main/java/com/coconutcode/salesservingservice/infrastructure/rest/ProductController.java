package com.coconutcode.salesservingservice.infrastructure.rest;

import com.coconutcode.salesservingservice.infrastructure.rest.model.CategoriesByDay;
import com.coconutcode.salesservingservice.infrastructure.rest.model.ProductSaleByDay;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {
    @Autowired
    private GetProductsSaleByDay getProductsSaleByDay;

    @Autowired
    private GetCategoriesByDay getCategoriesByDay;

    @GetMapping("/products/{productId}")
    public ProductSaleByDay getProductsSaleByDay(@PathVariable("productId") String productId,
                                                 @RequestParam("day") String day) {
        val productsSaleByDay = getProductsSaleByDay.getProductsSaleByDay(productId, day);
        return productsSaleByDay.orElseGet(() -> new ProductSaleByDay(productId, day, 0));
    }

    @GetMapping("/categories/{category}")
    public CategoriesByDay getCategoriesyDay(@PathVariable("category") String category,
                                             @RequestParam("day") String day) {
        val productsSaleByDay = getCategoriesByDay.getCategoriesByDay(category, day);
        return productsSaleByDay.orElseGet(() -> new CategoriesByDay(category, day, 0));
    }
}
