package com.wonstore.exception;

public class PointNotFound extends WonStoreException {

    private static final String MESSAGE = "해당 충전 내역이 없습니다.";

    public PointNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
