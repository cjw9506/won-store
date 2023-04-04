package com.wonstore.service;

import com.wonstore.entity.Member;
import com.wonstore.exception.LoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberServiceImpl memberService;

    public Member login(String userId, String password) throws LoginFailedException {
        Optional<Member> findMember = memberService.findByUsername(userId);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (password.equals(member.getPassword())) {
                return member;
            }
        }
        throw new LoginFailedException("아이디와 비밀번호가 일치하지 않습니다.");
    }


}
