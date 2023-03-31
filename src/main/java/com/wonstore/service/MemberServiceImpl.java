package com.wonstore.service;

import com.wonstore.entity.Member;
import com.wonstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long join(Member member) {
        //validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

//    private void validateDuplicateMember(Member member) {
//        List<Member> allName = memberRepository.findAllName(member.getUsername());
//        if (!allName) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

    @Override
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findByUsername(String loginId) {
        return findMembers().stream()
                .filter(m -> m.getUsername().equals(loginId))
                .findFirst();
    }


}

