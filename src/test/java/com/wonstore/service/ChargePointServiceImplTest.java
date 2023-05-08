package com.wonstore.service;

import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;

class ChargePointServiceImplTest {

    @Autowired ChargePointServiceImpl chargePointService;
    @Autowired MemberServiceImpl memberService;

    @Test
    void charge() {
        Member member = Member.builder().userId("qweqwe")
                .password("qweqweqwe")
                .email("test@naver.com")
                .username("testtest")
                .build();
        ChargePoint chargePoint = ChargePoint.builder().amount(100000)
                .member(member).build();
        Long charge = chargePointService.charge(chargePoint);
        ChargePoint findCharge = chargePointService.findOne(charge);
        Assertions.assertThat(findCharge.getAmount() == 100000);

    }
}