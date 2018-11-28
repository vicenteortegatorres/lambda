package com.coconutcode.infrastructure.persistence.model;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Data
public class ProductDayView implements Serializable {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private String productId;
    private String data;

    public ProductDayView(String productId, long timestamp) {
        this.productId = productId;
        this.data = getDay(timestamp);

    }

    private String getDay(long timestamp) {
        return new SimpleDateFormat(DATE_FORMAT).format(Date.from(Instant.ofEpochMilli(timestamp)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDayView that = (ProductDayView) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, data);
    }
}
