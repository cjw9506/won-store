package com.wonstore.service;

import com.wonstore.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Autowired MemberServiceImpl memberServiceImpl;

    @Test
    @Transactional
    public void joinTest() { //회원가입 테스트
        Member member = Member.builder().username("jiwon").build();
        Long memberId = memberServiceImpl.join(member);
        Member findMember = memberServiceImpl.findOne(memberId);
        Assertions.assertEquals(findMember.getId(), memberId);
    }

    @Test
    @Transactional
    public void DuplicateName() { //회원이름 중복 테스트
        Member memberA = Member.builder().username("jiwon").build();
        Member memberB = Member.builder().username("jiwon").build();
        memberServiceImpl.join(memberA);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberServiceImpl.join(memberB));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());

    }
}