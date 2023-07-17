package com.wonstore.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.login.LoginRequest;
import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import com.wonstore.dto.apiDto.member.UpdateMemberPassword;
import com.wonstore.dto.apiDto.member.UpdateMemberRequest;
import com.wonstore.entity.Member;
import com.wonstore.repository.MemberRepository;
import com.wonstore.service.AuthService;
import com.wonstore.service.MemberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("/api/members/new 요청시 - 회원가입")
    void test1() throws Exception {
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/members/new")
                .contentType(APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("/api/members/{memberId} 요청시 - 회원정보 수정")
    void test2() throws Exception {

        CreateMemberRequest member = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        Long memberId = memberService.join(member);

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        UpdateMemberRequest request = UpdateMemberRequest.builder()
                .username("지원정")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/members/{memberId}", memberId)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", jws)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test //Todo 비밀번호 변경시 암호화되어 저장되어야함
    @DisplayName("/api/members/{memberId}/password 요청시 - 회원 비밀번호 수정")
    void test3() throws Exception {

        CreateMemberRequest member = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        Long memberId = memberService.join(member);

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        UpdateMemberPassword request = UpdateMemberPassword.builder()
                .currentPassword("1234")
                .changePassword("12345")
                .verifyPassword("12345")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/members/{memberId}/password", memberId)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", jws)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/api/members 요청시 회원 전체 조회(비밀번호 제외)")
    void test4() throws Exception{
        CreateMemberRequest member1 = CreateMemberRequest.builder()
                .email("jungkt@naver.com")
                .username("지원")
                .password("234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구")
                .build();
        CreateMemberRequest member2 = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        memberService.join(member1);
        memberService.join(member2);

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        mockMvc.perform(get("/api/members")
                .contentType(APPLICATION_JSON)
                        .header("Authorization", jws))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].email").value("jungkt@naver.com"))
                .andExpect(jsonPath("$[1].email").value("jungkt0x@naver.com"))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/members/{memberId} 요청시 회원 단건 조회(비밀번호 제외)")
    void test5() throws Exception{
        CreateMemberRequest member = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("지원")
                .password("1234")
                .phoneNumber("010-5310-9506")
                .address("경기도 용인시 수지구")
                .build();

        Long memberId = memberService.join(member);

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        mockMvc.perform(get("/api/members/{memberId}", memberId)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", jws))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jungkt0x@naver.com"))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/members/{memberId} - 요청시 해당 회원 탈퇴 처리")
    void test6() throws Exception{
        CreateMemberRequest member = CreateMemberRequest.builder()
                .email("jungkt0x@naver.com")
                .username("정지원")
                .password("1234")
                .phoneNumber("01053109506")
                .address("경기도 용인시")
                .build();

        Long memberId = memberService.join(member);

        LoginRequest loginInfo = LoginRequest.builder()
                .email("jungkt0x@naver.com")
                .password("1234")
                .build();

        String jws = authService.login(loginInfo);

        mockMvc.perform(delete("/api/members/{memberId}", memberId)
                        .header("Authorization", jws))
                .andExpect(status().isOk())
                .andDo(print());
    }



}