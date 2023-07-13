package com.wonstore.exception;

public class LoginFailedException extends WonStoreException {

    private static final String MESSAGE = "로그인 정보가 맞지 않습니다.";

    public LoginFailedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
