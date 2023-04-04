package com.wonstore.controller;

import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.entity.SessionConst;
import com.wonstore.service.ItemServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemServiceImpl itemService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        if (loginMember == null) {
            // 로그인한 사용자가 아닌 경우 home으로 이동
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}
