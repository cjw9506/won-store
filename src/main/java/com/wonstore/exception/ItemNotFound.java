package com.wonstore.exception;

public class ItemNotFound extends WonStoreException{

    private static final String MESSAGE = "아이템을 찾을 수 없습니다.";

    public ItemNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
