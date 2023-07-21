package com.wonstore.service;

import com.wonstore.crypto.PasswordEncoder;
import com.wonstore.dto.apiDto.member.*;
import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.ChangePasswordException;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.IdNotFound;
import com.wonstore.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct //샘플 데이터
//    public void init() {
//        Member member1 = Member.builder()
//                .email("test@gmail.com")
//                .password("ekdl1212")
//                .build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .email("test2.gmail.com")
//                .password("ekdl1212")
//                .build();
//        memberRepository.save(member2);
//    }

    @Transactional//회원가입
    public Long join(CreateMemberRequest request) {
        Optional<Member> userOptional = memberRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new DuplicateEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encryptedPassword)
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        memberRepository.save(member);

        return member.getId();
    }

     //단건 조회 - id
    public MemberGetOneDto findOne(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IdNotFound::new);

        MemberGetOneDto response = MemberGetOneDto.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .currentPoint(member.getCurrentPoint())
                .build();

        return response;
    }

    // 전체 조회

    public List<MemberListDto> findMembers() {

        return memberRepository.findAll().stream()
                .map(m -> MemberListDto.builder()
                        .email(m.getEmail())
                        .username(m.getUsername())
                        .phoneNumber(m.getPhoneNumber())
                        .address(m.getAddress())
                        .currentPoint(m.getCurrentPoint())
                        .build())
                .collect(Collectors.toList());
    }


    @Transactional //회원 수정
    public Long updateMember(UpdateMemberRequest request, Long memberId) {
        Member findMember = memberRepository.findById(memberId).get();
        findMember.updateInfo(request.getUsername(), request.getPhoneNumber(), request.getAddress());

        return findMember.getId();
    }


    @Transactional //비밀번호 수정
    public void changePassword(Long memberId, UpdateMemberPassword request) {
        Member savedMember = memberRepository.findById(memberId).get();

        if (!passwordEncoder.matches(request.getCurrentPassword(), savedMember.getPassword())) {
            throw new ChangePasswordException();
        }
        if (passwordEncoder.matches(request.getChangePassword(), savedMember.getPassword())) {
            throw new ChangePasswordException();
        }
        if (!request.getChangePassword().equals(request.getVerifyPassword())) {
            throw new ChangePasswordException();
        }
        String changeEncryptedPassword = passwordEncoder.encrypt(request.getChangePassword());

        savedMember.changePassword(changeEncryptedPassword);

        log.info("{}님이 비밀번호를 수정하였습니다.", savedMember.getEmail());
        log.info("password = {}", savedMember.getPassword());
    }

    @Transactional //회원 삭제
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).get();
        log.info("{}님이 회원탈퇴 하였습니다.", member.getEmail());
        memberRepository.deleteById(id);
    }

}

