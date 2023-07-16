package com.wonstore.service;

import com.wonstore.config.AppConfig;
import com.wonstore.config.data.MemberSession;
import com.wonstore.crypto.PasswordEncoder;
import com.wonstore.dto.apiDto.login.LoginRequest;
import com.wonstore.entity.AuthToken;
import com.wonstore.entity.Member;
import com.wonstore.exception.LoginFailedException;
import com.wonstore.repository.AuthTokenRepository;
import com.wonstore.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthTokenRepository authTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppConfig appConfig;

    @Transactional
    public String login(LoginRequest request) {

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new LoginFailedException();
        }

        long curTime = System.currentTimeMillis();

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(key)
                .setExpiration(new Date(curTime + 300000))
                .setIssuedAt(new Date(curTime))
                .compact();

        AuthToken info = AuthToken.builder()
                .member(member)
                .token(jws)
                .build();

        authTokenRepository.save(info);

        return jws;
    }

//    @Transactional
//    public void logout(MemberSession session) {
//
//    }
}
