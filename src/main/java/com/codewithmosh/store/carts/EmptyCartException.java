package com.codewithmosh.store.carts;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("No item to order");
    }
}
