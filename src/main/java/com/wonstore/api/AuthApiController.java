package com.wonstore.api;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.login.LoginRequest;
import com.wonstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/api/login")
    public void login(@RequestBody LoginRequest request) {
        authService.login(request);
    }

//    @DeleteMapping("/api/logout")
//    public void logout(MemberSession session) {
//        authService.logout(session);
//    }
}
