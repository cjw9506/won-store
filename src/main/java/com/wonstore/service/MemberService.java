package com.wonstore.service;

import com.wonstore.dto.apiDto.member.*;
import com.wonstore.entity.Member;
import com.wonstore.exception.ChangePasswordException;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    //생성
    Long join(CreateMemberRequest request);

    //단건 조회
    MemberDto findOne(Long memberId);

    //전체 조회
    List<MemberListDto> findMembers();

    //수정
    Long updateMember(UpdateMemberRequest request, Long memberId);

    //비밀번호 변경
    void changePassword(Long id, UpdateMemberPassword request) throws ChangePasswordException;

    //회원 삭제
    void deleteMember(Long id);
}