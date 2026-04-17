package com.codewithmosh.store.users;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException() {
        super("This email is already registered");
    }
}
