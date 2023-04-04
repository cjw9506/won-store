package com.wonstore.service;

import com.wonstore.entity.Address;
import com.wonstore.entity.Member;
import com.wonstore.exception.DuplicateEmailException;
import com.wonstore.exception.DuplicateIdException;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Long join(Member member) throws DuplicateIdException, DuplicateEmailException;

    void changePassword(Long id, String newPassword);

    Optional<Member> findOne(Long memberId);

    List<Member> findMembers();

    Optional<Member> findByUsername(String username);

    void updateMember(Long id, String username, String phoneNumber, Address address);
}