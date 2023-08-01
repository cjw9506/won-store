package com.wonstore.exception;

public class OrderNotFound extends WonStoreException {

    private static final String MESSAGE = "해당 주문을 찾을 수 없습니다.";

    public OrderNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
