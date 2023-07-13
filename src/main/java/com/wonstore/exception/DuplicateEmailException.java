package com.wonstore.exception;

public class DuplicateEmailException extends WonStoreException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
