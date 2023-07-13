package com.wonstore.exception;

public class ChangePasswordException extends WonStoreException{

    private final static String MESSAGE = "비밀번호를 확인해주시기 바랍니다.";

    public ChangePasswordException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
