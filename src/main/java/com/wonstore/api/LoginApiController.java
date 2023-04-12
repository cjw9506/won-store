package com.wonstore.api;

import com.wonstore.controller.LoginController;
import com.wonstore.dto.apiDto.login.LoginRequest;
import com.wonstore.dto.apiDto.login.LoginResponse;
import com.wonstore.entity.Member;
import com.wonstore.entity.SessionConst;
import com.wonstore.exception.LoginFailedException;
import com.wonstore.service.LoginServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final MemberServiceImpl memberService;
    private final LoginServiceImpl loginService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request,
                               HttpSession session) throws LoginFailedException {

        Optional<Member> user = memberService.findByUsername(request.getId());

        if (!user.isPresent() || !user.get().getUserId().equals(request.getId())) {
            throw new LoginFailedException("아이디를 확인해주세요.");
        }

        if (!user.get().getPassword().equals(request.getPassword())) {
            throw new LoginFailedException("비밀번호를 확인해주세요.");
        }

        Member loginMember = loginService.login(request.getId(), request.getPassword());
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return new LoginResponse(true, "로그인에 성공하였습니다.");
    }

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
