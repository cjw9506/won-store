package com.wonstore.service;

import com.wonstore.entity.Member;
import com.wonstore.exception.LoginFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final MemberServiceImpl memberService;

    @Override
    public Member login(String userId, String password) throws LoginFailedException {
        Optional<Member> findMember = memberService.findByUsername(userId);
        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (password.equals(member.getPassword())) {

                log.info("{}님이 로그인하였습니다.", member.getUserId());

                return member;
            }
        }
        throw new LoginFailedException("아이디와 비밀번호가 일치하지 않습니다.");

    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

    }

}
