package com.codewithmosh.store.products;

import java.util.UUID;

public class ProductNotFoundInCartException extends ProductNotFoundException {

    public ProductNotFoundInCartException(Long productId, UUID cartId) {
        super(productId, cartId);
    }

}
