package com.codewithmosh.store.products;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProductNotFoundException extends RuntimeException{
    private Long productId;
    private UUID cartId;

    public ProductNotFoundException(Long productId, UUID cartId) {
        super("Product with id " + productId + " not found in cart " + cartId );
        this.productId = productId;
        this.cartId = cartId;
    }
}
