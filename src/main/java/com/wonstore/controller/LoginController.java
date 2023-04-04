package com.wonstore.controller;

import com.wonstore.dto.LoginDto;
import com.wonstore.entity.Member;
import com.wonstore.entity.SessionConst;
import com.wonstore.exception.LoginFailedException;
import com.wonstore.service.LoginService;
import com.wonstore.service.LoginServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginServiceImpl loginService;
    private final MemberServiceImpl memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto")LoginDto loginDto) {
        return "login/loginForm";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto,
                        HttpServletRequest request,
                        Model model) {
        try {
            Optional<Member> findUser = memberService.findByUsername(loginDto.getUserId());

            if (!findUser.isPresent() || !findUser.get().getUserId().equals(loginDto.getUserId())) {
                throw new LoginFailedException("아이디를 확인해주세요.");
            }

            if (!findUser.get().getPassword().equals(loginDto.getPassword())) {
                throw new LoginFailedException("비밀번호를 확인해주세요.");
            }

            Member loginMember = loginService.login(loginDto.getUserId(), loginDto.getPassword());
            log.info("login? {}", loginMember.getUserId());

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            return "redirect:/";

        } catch (LoginFailedException e) {
            String errorMessage = e.getMessage();
            logger.error(errorMessage, e);
            model.addAttribute("errorMessage", errorMessage);
            return "login/loginForm";
        }
    }



    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}