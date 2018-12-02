package com.coconutcode.salesbatchservice.infrastructure.persistence.views.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class CompositeKey implements Serializable {
    private String topic;
    private String day;
}
