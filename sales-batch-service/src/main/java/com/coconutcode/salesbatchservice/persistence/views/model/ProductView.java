package com.coconutcode.salesbatchservice.persistence.views.model;

import com.coconutcode.salesbatchservice.util.DateUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ProductView implements Serializable {

    @Id
    private String id;
    private String topic;
    private String day;
    private long units;
    private ViewType viewType;

    public ProductView(String productId, long timestamp) {
        this.topic = productId;
        this.day = DateUtil.getDay(timestamp);
    }

    public ProductView() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductView that = (ProductView) o;
        return Objects.equals(topic, that.topic) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, day);
    }
}
