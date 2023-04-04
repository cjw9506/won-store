package com.wonstore.service;

import com.wonstore.entity.Member;
import com.wonstore.exception.LoginFailedException;
import com.wonstore.repository.MemberRepository;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface LoginService {

    public Member login(String username, String password) throws LoginFailedException;


}



