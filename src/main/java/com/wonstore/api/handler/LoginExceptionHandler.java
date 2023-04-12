package com.wonstore.api.handler;

import com.wonstore.dto.apiDto.login.LoginResponse;
import com.wonstore.exception.LoginFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    @ResponseBody
    public LoginResponse handleLoginFailedException(LoginFailedException e) {
        return new LoginResponse(false, e.getMessage());
    }
}
