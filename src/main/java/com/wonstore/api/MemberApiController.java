package com.wonstore.api;

import com.wonstore.dto.apiDto.*;
import com.wonstore.dto.apiDto.member.*;
import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.entity.Review;
import com.wonstore.exception.ChangePasswordException;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.service.MemberServiceImpl;
import com.wonstore.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberServiceImpl memberService;
    private final ReviewServiceImpl reviewService;

    @PostMapping("/api/members/new") //회원가입 - user
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody CreateMemberRequest request)
            throws DuplicateIdException, DuplicateEmailException {

        Long id = memberService.join(request.getUserId(), request.getEmail(), request.getUsername(), request.getPassword(), request.getPhoneNumber(), request.getAddress());

        return ResponseEntity.created(URI.create("/api/members/" + id)).body(new CreateMemberResponse(id));

    }

    @PutMapping("/api/members/{memberId}") //회원 정보 수정 -> 이름, 주소, 연락처 만 수정가능 - user
    public UpdateMemberResponse updateMember(@PathVariable("memberId") Long memberId,
                                             @RequestBody UpdateMemberRequest request) {

        memberService.updateMember(memberId, request.getUsername(), request.getPhoneNumber(), new Address(request.getAddress()));

        return new UpdateMemberResponse(memberId);
    }

    @PutMapping("/api/members/{memberId}/password")
    public UpdateMemberResponse updateMemberPassword(@PathVariable("memberId") Long memberId,
                                                     @RequestBody UpdateMemberPassword request) throws ChangePasswordException {
        memberService.changePassword(memberId, request.getCurrentPassword(), request.getChangePassword(), request.getVerifyPassword());

        return new UpdateMemberResponse(memberId);
    }

    @GetMapping("/api/members") //전체 회원 조회 - Admin
    public Result members() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberListDto> collect = findMembers.stream()
                .map(m -> new MemberListDto(m.getUserId()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    //단건 조회 - Admin
    @GetMapping("/api/members/{memberId}")
    public MemberDto member(@PathVariable("memberId") Long memberId) {
        Member findMember = memberService.findOne(memberId);
        MemberDto member = new MemberDto(findMember.getUserId(),
                findMember.getEmail(),
                findMember.getUsername(),
                findMember.getPassword(),
                findMember.getPhoneNumber(),
                findMember.getAddress(),
                findMember.getCurrentPoint());
        return member;
    }

    @GetMapping("/api/members/review/{memberId}") //회원 리뷰 조회 -> 이건 안쓸 확률이 높음(리뷰에서 조회)
    public Result memberReviews(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findOne(memberId);
        List<Review> reviews = member.getReviews();
        List<MemberReview> collect = reviews.stream()
                .map(r -> new MemberReview(r.getItem().getId(), r.getTitle(), r.getContent()))
                .collect(Collectors.toList());

        return new Result(collect);
    }


    @DeleteMapping("/api/members/{memberId}") //회원 탈퇴 - User
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "회원탈퇴처리되었습니다.");

        return ResponseEntity.ok(response);
    }

}
