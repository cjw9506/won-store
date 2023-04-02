package com.wonstore.controller;

import com.wonstore.dto.LoginDto;
import com.wonstore.dto.MemberDto;
import com.wonstore.entity.Member;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberDto")MemberDto memberDto) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("memberDto") @Validated MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }
        memberService.join(Member.dtoToEntity(memberDto));
        return "redirect:/";
    }
}
