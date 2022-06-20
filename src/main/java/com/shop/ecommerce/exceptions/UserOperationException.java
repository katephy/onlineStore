package com.shop.ecommerce.exceptions;

public class UserOperationException extends RuntimeException{

    public UserOperationException(String msg) {
        super(msg);
    }
}
