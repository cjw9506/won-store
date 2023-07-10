package com.wonstore.repository;


import com.wonstore.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    @Query("select m from Member m")
//    public List<Member> findAllName(String name);

    Optional<Member> findByEmail(String email);

}
