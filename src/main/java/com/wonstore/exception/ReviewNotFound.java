package com.wonstore.exception;

public class ReviewNotFound extends WonStoreException{

    private static final String MESSAGE = "리뷰를 찾을 수 없습니다.";

    public ReviewNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
