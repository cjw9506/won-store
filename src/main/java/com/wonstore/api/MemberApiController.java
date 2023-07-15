package com.wonstore.api;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.member.*;
import com.wonstore.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    //private final ReviewServiceImpl reviewService;

    //회원가입
    @PostMapping("/api/members/new")
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody CreateMemberRequest request) {
        Long id = memberService.join(request);
        return ResponseEntity.created(URI.create("/api/members/" + id)).body(new CreateMemberResponse(id));

    }

    //회원 정보 수정 -> 이름, 주소, 연락처 수정가능
    @PutMapping("/api/members/{memberId}")
    public UpdateMemberResponse updateMember(@PathVariable("memberId") Long memberId,
                                             @RequestBody UpdateMemberRequest request,
                                             MemberSession session) {
        memberService.updateMember(request, memberId);
        return new UpdateMemberResponse(memberId);
    }

    //비밀번호 변경
    @PutMapping("/api/members/{memberId}/password")
    public UpdateMemberResponse updateMemberPassword(@PathVariable("memberId") Long memberId,
                                                     @RequestBody UpdateMemberPassword request,
                                                     MemberSession session) {
        memberService.changePassword(memberId, request);
        return new UpdateMemberResponse(memberId);
    }

    //전체 조회
    @GetMapping("/api/members")
    public List<MemberListDto> findMembers(MemberSession session) {
        return memberService.findMembers();
    }

    //단건 조회
    @GetMapping("/api/members/{memberId}")
    public MemberGetOneDto member(@PathVariable("memberId") Long memberId,
                                  MemberSession session) {
        MemberGetOneDto response = memberService.findOne(memberId);
        return response;

    }

    //회원 탈퇴
    @DeleteMapping("/api/members/{memberId}")
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable("memberId") Long memberId,
                                                            MemberSession session) {
        memberService.deleteMember(memberId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원탈퇴처리되었습니다.");
        return ResponseEntity.ok(response);
    }

}
