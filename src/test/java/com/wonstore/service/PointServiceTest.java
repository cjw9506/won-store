package com.wonstore.service;

import com.wonstore.dto.apiDto.point.PointRequest;
import com.wonstore.dto.apiDto.point.PointResponse;
import com.wonstore.entity.Member;
import com.wonstore.entity.Point;
import com.wonstore.repository.MemberRepository;
import com.wonstore.repository.PointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PointService pointService;

    new ConcurrentHashMap
    @BeforeEach
    void clean() {
        pointRepository.deleteAll();
    }

//    @Test
//    @DisplayName("포인트 충전 테스트")
//    void test1() {
//        Member member = Member.builder()
//                .email("jungkt0x@naver.com")
//                .username("정지원")
//                .password("1234")
//                .phoneNumber("010-5310-9506")
//                .address("경기도 용인시 수지구 풍덕천동")
//                .build();
//
//        memberRepository.save(member);
//
//        PointRequest request = PointRequest.builder()
//                .memberId(member.getId())
//                .point(10000)
//                .build();
//
//        pointService.charge(request);
//
//        Point point = pointRepository.findAll().get(0);
//
//        Assertions.assertEquals(1L, pointRepository.count());
//        Assertions.assertEquals(point.getAmount(), 10000);
//    }
//
//    @Test
//    @DisplayName("포인트 전체 조회")
//    void test2() {
//        Member member = Member.builder()
//                .email("jungkt0x@naver.com")
//                .username("정지원")
//                .password("1234")
//                .phoneNumber("010-5310-9506")
//                .address("경기도 용인시 수지구 풍덕천동")
//                .build();
//
//        memberRepository.save(member);
//
//        PointRequest charge1 = PointRequest.builder()
//                .memberId(member.getId())
//                .point(10000)
//                .build();
//
//        pointService.charge(charge1);
//
//        PointRequest charge2 = PointRequest.builder()
//                .memberId(member.getId())
//                .point(20000)
//                .build();
//
//        pointService.charge(charge2);
//
//        List<PointResponse> points = pointService.getList();
//
//        Assertions.assertEquals(points.size(), 2L);
//
//    }
//
//    @Test
//    @DisplayName("포인트 내역 단건 조회")
//    void test3() {
//        Member member = Member.builder()
//                .email("jungkt0x@naver.com")
//                .username("정지원")
//                .password("1234")
//                .phoneNumber("010-5310-9506")
//                .address("경기도 용인시 수지구 풍덕천동")
//                .build();
//
//        Member savedMember = memberRepository.save(member);
//
//        PointRequest request = PointRequest.builder()
//                .memberId(member.getId())
//                .point(10000)
//                .build();
//
//        pointService.charge(request);
//
//        PointResponse response = pointService.getList().get(0);
//        Assertions.assertEquals(response.getChargedPoint(), 10000);
//
//    }
}