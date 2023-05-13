package com.wonstore.service;

import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

//    @Autowired MemberServiceImpl memberServiceImpl;
//
//    @Test
//    @Transactional
//    public void joinTest() throws DuplicateIdException, DuplicateEmailException { //회원가입 테스트
//        Member member = Member.builder().username("jiwon").build();
//        Long memberId = memberServiceImpl.join(member);
//        Member findMember = memberServiceImpl.findOne(memberId);
//        Assertions.assertEquals(findMember.getId(), memberId);
//    }
//
//    @Test
//    @Transactional
//    public void DuplicateName() throws DuplicateIdException, DuplicateEmailException { //회원이름 중복 테스트
//        Member memberA = Member.builder().userId("cjw9506").build();
//            Member memberB = Member.builder().userId("cjw9506").build();
//        memberServiceImpl.join(memberA);
//
//        try {
//            memberServiceImpl.join(memberB);
//            fail("예외가 발생해야 합니다.");
//        } catch (IllegalStateException e) {
//            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
//
//    }

}