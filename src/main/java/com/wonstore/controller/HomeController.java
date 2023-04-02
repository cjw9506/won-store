package com.wonstore.controller;

import com.wonstore.entity.Member;
import com.wonstore.entity.SessionConst;
import com.wonstore.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberServiceImpl memberService;

    @GetMapping("/")
    public String home() {
        return "home";
    }
}