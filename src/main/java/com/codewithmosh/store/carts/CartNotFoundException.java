package com.codewithmosh.store.carts;

import lombok.Getter;

import java.util.UUID;


@Getter
public class CartNotFoundException extends RuntimeException{
    private UUID id;

    public CartNotFoundException(UUID id) {
        super("Cart is not found");
        this.id = id;
    }
}
