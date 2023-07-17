package com.wonstore.service;

import com.wonstore.crypto.PasswordEncoder;
import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import com.wonstore.dto.apiDto.member.MemberGetOneDto;
import com.wonstore.dto.apiDto.member.MemberListDto;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class MemberServiceTest {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void test1() {
        Member member = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Member savedMember = memberRepository.save(member);

        Assertions.assertEquals(savedMember.getUsername(), "정지원");
        Assertions.assertEquals(memberRepository.count(), 1L);
    }


    @Test
    @DisplayName("회원가입시 비밀번호 암호화 테스트")
    void test2() {

        String encryptedPassword = passwordEncoder.encrypt("1234");

        Member member = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password(encryptedPassword)
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Member savedMember = memberRepository.save(member);

        Assertions.assertNotEquals("1234", savedMember.getPassword());
    }

    @Test
    @DisplayName("회원 단건 조회")
    void test3() {

        Member member = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Member savedMember = memberRepository.save(member);
        MemberGetOneDto findOneMember = memberService.findOne(savedMember.getId());

        Assertions.assertEquals(findOneMember.getEmail(), member.getEmail());
    }

    @Test
    @DisplayName("회원 전체 조회")
    void test4() {
        Member member1 = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Member member2 = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberListDto> members = memberService.findMembers();

        Assertions.assertEquals(members.size(), 2L);
    }

    @DisplayName("회원 삭제")
    @Test
    void test5() {
        Member member = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Member savedMember = memberRepository.save(member);

        memberRepository.deleteById(savedMember.getId());

        Assertions.assertEquals(memberRepository.count(), 0L);
    }

    @DisplayName("회원가입시 이메일 중복 테스트")
    @Test
    void test6() {
        Member member1 = Member.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        memberRepository.save(member1);

        CreateMemberRequest member2 = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구 풍덕천동")
                .build();

        Assertions.assertThrows(DuplicateEmailException.class, () ->
            memberService.join(member2));
    }
}