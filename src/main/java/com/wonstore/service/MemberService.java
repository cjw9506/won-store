package com.wonstore.service;

import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.ChangePasswordException;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    //생성
    Long join(String userId, String password, String username, String email, String phoneNumber, String address) throws DuplicateIdException, DuplicateEmailException;

    //단건 조회
    Member findOne(Long memberId);

    //전체 조회
    List<Member> findMembers();

    //단건 조회 - 이름
    Optional<Member> findByUsername(String username);

    //수정
    void updateMember(Long id, String username, String phoneNumber, Address address);

    //비밀번호 변경
    void changePassword(Long id, String currentPassword, String changePassword, String verifyPassword) throws ChangePasswordException;

    //회원 삭제
    void deleteMember(Long id);
}