package com.wonstore.service;

import com.wonstore.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Long join(Member member);

    Optional<Member> findOne(Long memberId);

    List<Member> findMembers();

    Optional<Member> findByUsername(String username);
}