package com.wonstore.service;

import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import com.wonstore.dto.apiDto.member.UpdateMemberRequest;
import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @PostConstruct //샘플 데이터
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
    @Override //회원가입
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

    @Override //단건 조회 - id
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override // 단건 조회 - username
    public Optional<Member> findByUsername(String loginId) {
        return findMembers().stream()
                .filter(m -> m.getUserId().equals(loginId))
                .findFirst();
    }

    @Override// 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional //회원 수정
    public void updateMember(Long id, String username, String phoneNumber, Address address) {
        Member savedMember = memberRepository.findById(id).get();
        savedMember.updateInfo(username, phoneNumber, address);
    }

    @Override
    @Transactional //비밀번호 수정
    public void changePassword(Long id, String newPassword) {
        Member savedMember = memberRepository.findById(id).get();
        savedMember.changePassword(newPassword);
    }

    @Transactional //회원 삭제
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }


}

