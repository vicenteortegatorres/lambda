package com.coconutcode.salesservingservice.infrastructure.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CompositeKey implements Serializable {
    private String topic;
    private String day;
}
