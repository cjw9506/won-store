package com.wonstore.controller;

import com.wonstore.dto.mvcDto.MemberDto;
import com.wonstore.dto.mvcDto.PasswordDto;
import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberDto")MemberDto memberDto) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("memberDto") @Validated MemberDto memberDto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        try {
            Member member = dtoToEntity(memberDto);
            memberService.join(member.getUserId(), member.getEmail(), member.getUsername(),
                    member.getPassword(), member.getPhoneNumber(), member.getAddress().getDetailedAddress());
        } catch (DuplicateIdException e) {
            String errorMessage = e.getMessage();
            logger.error(errorMessage, e);
            model.addAttribute("idErrorMessage", errorMessage);
            return "members/addMemberForm";
        } catch (DuplicateEmailException e) {
            String errorMessage = e.getMessage();
            logger.error(errorMessage, e);
            model.addAttribute("emailErrorMessage", errorMessage);
            return "members/addMemberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/{memberId}/edit") //인터셉터 관리
    public String edit(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        MemberDto memberDto = entityToDto(member);
        model.addAttribute("memberDto", memberDto);
        log.info("memberId = {}", memberId);
        return "members/editMemberForm";
    }
    //인터셉터 관리
    @PostMapping("/{memberId}/edit") //여기도 이메일, 아이디 중복처리 해줘야함 -> 완료
    public String update(@PathVariable("memberId") Long memberId,
                         @ModelAttribute("memberDto") @Validated MemberDto memberDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/editMemberForm";
        }

        memberService.updateMember(memberId, memberDto.getUsername(), memberDto.getPhoneNumber(), new Address(memberDto.getDetailedAddress()));
        log.info("memberId = {}", memberId);
        return "redirect:/";
    }

    @GetMapping("/{memberId}/edit/password")
    public String editPassword(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        PasswordDto passwordDto = passwordToDto(member);
        model.addAttribute("passwordDto", passwordDto);
        return "members/editPasswordForm";

    }

    @PostMapping("/{memberId}/edit/password")
    public String updatePassword(@PathVariable("memberId") Long memberId,
                                 @ModelAttribute("passwordDto") @Validated PasswordDto passwordDto,
                                 BindingResult bindingResult) {
        Member findMember = memberService.findOne(memberId);
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirm())) {
            bindingResult.rejectValue(
                    "newPasswordConfirm", "error.passwordDto", "새 비밀번호와 새 비밀번호 확인이 다릅니다.");
        } else if (!passwordDto.getCurrentPassword().equals(findMember.getPassword())) {
            bindingResult.rejectValue(
                    "currentPassword", "error.passwordDto", "현재 비밀번호가 틀립니다.");
        }

        if (bindingResult.hasErrors()) {
            return "members/editPasswordForm";
        }

        //memberService.changePassword(memberId,passwordDto.getNewPassword());
        return "redirect:/";
    }

    // dto -> entity
    private Member dtoToEntity(MemberDto memberDto) {
        Member member = Member.builder()
                .userId(memberDto.getUserId())
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .phoneNumber(memberDto.getPhoneNumber())
                .address(new Address(memberDto.getDetailedAddress()))
                .build();
        return member;
    }

    // entity -> dto
    private MemberDto entityToDto(Member member) {
        Address address = member.getAddress();
        MemberDto dto = MemberDto.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .username(member.getUsername())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .detailedAddress(address != null? address.getDetailedAddress() :  null)
                .build();
        return dto;
    }

    //현재 비밀번호 떤지기
    private PasswordDto passwordToDto(Member member) {
        PasswordDto dto = PasswordDto.builder()
                .currentPassword(member.getPassword())
                .build();
        return dto;
    }
}
