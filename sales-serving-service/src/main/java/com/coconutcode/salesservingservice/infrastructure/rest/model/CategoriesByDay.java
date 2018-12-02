package com.coconutcode.salesservingservice.infrastructure.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoriesByDay {
    private String category;
    private String day;
    private long units;
}
