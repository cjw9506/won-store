package com.wonstore.service;

import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import com.wonstore.dto.apiDto.member.UpdateMemberRequest;
import com.wonstore.entity.Address;
import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Member;
import com.wonstore.exception.ChangePasswordException;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
    public Long join(String userId, String email, String username, String password, String phoneNumber, String address) throws DuplicateIdException, DuplicateEmailException {
        List<Member> findMembers = memberRepository.findAll();

        for (Member m : findMembers) {
            if (m.getUserId().equals(userId)) {
                throw new DuplicateIdException("이미 존재하는 회원입니다.");
            }
            if (m.getEmail().equals(address)) {
                throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
            }
        }
        Member member = Member.createMember(userId, email, username, password, phoneNumber, address);
        Member savedMember = memberRepository.save(member);

        log.info("{}님이 회원가입 하였습니다.", savedMember.getUserId());
        log.info("password = {}", savedMember.getPassword());
        log.info("username = {}", savedMember.getUsername());
        log.info("email = {}", savedMember.getEmail());
        log.info("phoneNumber = {}", savedMember.getPhoneNumber());
        log.info("address = {}", savedMember.getAddress().getDetailedAddress());

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

        log.info("{}님이 회원정보를 수정하였습니다.", savedMember.getUsername());
        log.info("username = {}", savedMember.getUsername());
        log.info("email = {}", savedMember.getEmail());
        log.info("phoneNumber = {}", savedMember.getPhoneNumber());
        log.info("address = {}", savedMember.getAddress().getDetailedAddress());
    }

    @Override
    @Transactional //비밀번호 수정
    public void changePassword(Long id, String currentPassword, String changePassword, String verifyPassword) throws ChangePasswordException {
        Member savedMember = memberRepository.findById(id).get();

        if (!savedMember.getPassword().equals(currentPassword)) {
            throw new ChangePasswordException("현재 비밀번호가 일치하지 않습니다.");
        }
        if (savedMember.getPassword().equals(changePassword) || savedMember.getPassword().equals(verifyPassword)) {
            throw new ChangePasswordException("변경할 비밀번호가 현재비밀번호와 같습니다.");
        }
        if (!changePassword.equals(verifyPassword)) {
            throw new ChangePasswordException("변경할 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        savedMember.changePassword(changePassword);

        log.info("{}님이 비밀번호를 수정하였습니다.", savedMember.getUserId());
        log.info("password = {}", savedMember.getPassword());
    }

    @Override
    @Transactional //회원 삭제
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).get();
        log.info("{}님이 회원탈퇴 하였습니다.", member.getUserId());
        memberRepository.deleteById(id);
    }

    //충전내역 조회 - 이것도 구지..?
    public List<ChargePoint> chargeHistory(Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getPoint();
    }



}

