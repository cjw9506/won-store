package com.wonstore.api;

import com.wonstore.dto.apiDto.*;
import com.wonstore.dto.apiDto.member.*;
import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberServiceImpl memberService;

    @PostMapping("/api/members") //회원가입
    public ResponseEntity<CreateMemberResponse> saveMemberV2(@RequestBody CreateMemberRequest request) throws DuplicateIdException, DuplicateEmailException {

        Member member = dtoToEntity(request);
        Long id = memberService.join(member);

        log.info("id = {}", memberService.findOne(id).getId());
        log.info("email = {}", memberService.findOne(id).getEmail());
        log.info("username = {}", memberService.findOne(id).getUsername());
        log.info("password = {}", memberService.findOne(id).getPassword());
        log.info("phoneNumber = {}", memberService.findOne(id).getPhoneNumber());
        log.info("address = {}", memberService.findOne(id).getAddress().getDetailedAddress());

        return ResponseEntity.created(URI.create("/api/members/" + id)).body(new CreateMemberResponse(id));

    }

    @PutMapping("api/members/{id}") //회원 정보 수정 -> 이름, 주소, 연락처 만 수정가능
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody UpdateMemberRequest request) {


        memberService.updateMember(id, request.getUsername(), request.getPhoneNumber(), new Address(request.getAddress()));

        log.info("id = {}", memberService.findOne(id).getId());
        log.info("email = {}", memberService.findOne(id).getEmail());
        log.info("username = {}", memberService.findOne(id).getUsername());
        log.info("password = {}", memberService.findOne(id).getPassword());
        log.info("phoneNumber = {}", memberService.findOne(id).getPhoneNumber());
        log.info("address = {}", memberService.findOne(id).getAddress().getDetailedAddress());

        return new UpdateMemberResponse(id);
    }

    @GetMapping("/api/members") //전체 회원 조회
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberListDto> collect = findMembers.stream()
                .map(m -> new MemberListDto(m.getUserId()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/members/{memberId}") //단건 조회
    public Result memberV2(@PathVariable("memberId") Long memberId) {
        Member findMember = memberService.findOne(memberId);

        return new Result(findMember);
    }

    @DeleteMapping("/api/members/{id}") //회원 삭제
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
    }

    // dto -> entity
    private Member dtoToEntity(CreateMemberRequest request) {
        Member member = Member.builder()
                .userId(request.getId())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .address(new Address(request.getAddress()))
                .build();
        return member;
    }
}
