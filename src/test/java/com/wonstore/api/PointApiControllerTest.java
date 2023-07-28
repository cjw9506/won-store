package com.wonstore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonstore.config.data.MemberSession;
import com.wonstore.crypto.PasswordEncoder;
import com.wonstore.dto.apiDto.login.LoginRequest;
import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import com.wonstore.dto.apiDto.point.PointRequest;
import com.wonstore.entity.Member;
import com.wonstore.repository.MemberRepository;
import com.wonstore.repository.PointRepository;
import com.wonstore.service.AuthService;
import com.wonstore.service.MemberService;
import com.wonstore.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PointApiControllerTest {
    static class RequestWrapper {
        private MemberSession session;
        private PointRequest request;

        public MemberSession getSession() {
            return session;
        }

        public PointRequest getRequest() {
            return request;
        }

        public void setSession(MemberSession session) {
            this.session = session;
        }

        public void setRequest(PointRequest request) {
            this.request = request;
        }
    }

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PointService pointService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PointRepository pointRepository;

    @BeforeEach
    void login() {

        pointRepository.deleteAll();
        memberRepository.deleteAll();

        CreateMemberRequest member = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        memberService.join(member);

    }

    @Test
    @DisplayName("/api/charge - 요청시 포인트 충전")
    void test1() throws Exception{

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        MemberSession session = MemberSession.builder()
                .id(1L)
                .build();
        PointRequest request = PointRequest.builder()
                .point(10000)
                .build();

        RequestWrapper wrapper = new RequestWrapper();
        wrapper.setSession(session);
        wrapper.setRequest(request);

        String json = objectMapper.writeValueAsString(wrapper);
        mockMvc.perform(post("/api/charge")
                        .header("Authorization", jws)
                    .contentType(APPLICATION_JSON)
                    .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/api/charge - get 요청시 모든 충전내역 조회")
    void test2() {

//        List<PointRequest> requests = LongStream.range(0, 10)
//                .mapToObj(i -> PointRequest.builder()
//                        .memberId(i)
//                        .point(10000)
//                        .build())
//                .collect(Collectors.toList());

        //pointService.saveAll(requests);
    }



}