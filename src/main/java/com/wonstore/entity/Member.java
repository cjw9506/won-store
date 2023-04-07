package com.wonstore.entity;

import com.wonstore.dto.mvcDto.MemberDto;
import com.wonstore.dto.mvcDto.PasswordDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId; //회원 아이디

    private String email; //회원 이메일

    private String username; //회원 이름

    private String password; //회원 비밀번호

    private String phoneNumber; //폰 번호

    @Embedded
    private Address address; //회원 주소

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //회원 정보 수정
    public void updateInfo(String username, String phoneNumber, Address address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    //패스워드 수정
    public void changePassword(String password) {
        this.password = password;
    }

}
