package com.wonstore.exception;

public class IdNotFound extends WonStoreException{

    private static final String MESSAGE = "아이디를 찾을 수 없습니다.";

    public IdNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
