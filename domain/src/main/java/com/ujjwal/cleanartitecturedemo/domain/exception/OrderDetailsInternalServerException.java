package com.ujjwal.cleanartitecturedemo.domain.exception;

public class OrderDetailsInternalServerException extends RuntimeException{
    public String exceptionMessage;

    public OrderDetailsInternalServerException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
