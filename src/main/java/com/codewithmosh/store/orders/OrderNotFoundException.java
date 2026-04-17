package com.codewithmosh.store.orders;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
    private Long orderId;

    public OrderNotFoundException(Long orderId) {
        super("Order is not found");
        this.orderId = orderId;
    }
}
