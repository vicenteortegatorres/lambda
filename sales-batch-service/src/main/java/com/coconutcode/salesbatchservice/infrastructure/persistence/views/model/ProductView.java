package com.coconutcode.salesbatchservice.infrastructure.persistence.views.model;

import com.coconutcode.salesbatchservice.common.DateUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductView implements Serializable {

    @Id
    private CompositeKey id;

    private long units;
    private ViewType viewType;

    public ProductView(String productId, long timestamp) {
        this.getId().setTopic(productId);
        this.getId().setDay(DateUtil.getDay(timestamp));
    }

    public CompositeKey getId() {
        if(!Optional.ofNullable(id).isPresent()) {
            id = new CompositeKey();
        }
        return id;
    }

    public void setTopic(String topic) {
        this.getId().setTopic(topic);
    }

    public String getTopic() {
        return this.getId().getTopic();
    }

    public void setDay(String day) {
        this.getId().setDay(day);
    }

    public String getDay() {
        return this.getId().getDay();
    }
}
