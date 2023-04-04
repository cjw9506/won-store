package com.wonstore.service;

import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        Member member1 = Member.builder()
                .userId("cjw9506")
                .email("test@gmail.com")
                .password("ekdl1212")
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .userId("jungkt0x")
                .email("test2.gmail.com")
                .password("ekdl1212")
                .build();
        memberRepository.save(member2);
    }

    @Transactional
    @Override
    public Long join(Member member) throws DuplicateIdException, DuplicateEmailException {
        List<Member> findMembers = memberRepository.findAll();

        for (Member m : findMembers) {
            if (m.getUserId().equals(member.getUserId())) {
                throw new DuplicateIdException("이미 존재하는 회원입니다.");
            }
            if (m.getEmail().equals(member.getEmail())) {
                throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
            }
        }

        memberRepository.save(member);
        return member.getId();
    }

    @Override
    @Transactional
    public void changePassword(Long id, String newPassword) {
        Member savedMember = memberRepository.findById(id).get();
        savedMember.changePassword(newPassword);
    }


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
                .filter(m -> m.getUserId().equals(loginId))
                .findFirst();
    }

    @Override
    @Transactional
    public void updateMember(Long id, String username, String phoneNumber, Address address) {
        Member savedMember = memberRepository.findById(id).get();
        savedMember.updateInfo(username, phoneNumber, address);
    }


}

