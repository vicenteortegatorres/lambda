package com.coconutcode.salesservingservice.infrastructure.rest;

import com.coconutcode.salesservingservice.infrastructure.rest.model.CategoriesByDay;

import java.util.Optional;

public interface GetCategoriesByDay {
    Optional<CategoriesByDay> getCategoriesByDay(String category, String day);
}
