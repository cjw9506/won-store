package com.wonstore.entity;

import com.wonstore.dto.MemberDto;
import com.wonstore.dto.PasswordDto;
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

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public static Member dtoToEntity(MemberDto memberDto) {
        Member member = Member.builder()
                .userId(memberDto.getUserId())
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .phoneNumber(memberDto.getPhoneNumber())
                .address(new Address(memberDto.getDetailedAddress()))
                .build();
        return member;
    }
    public static MemberDto entityToDto(Member member) {
        Address address = member.getAddress();
        MemberDto dto = MemberDto.builder()
                //.id(member.getId())
                .userId(member.getUserId())
                .email(member.getEmail())
                .username(member.getUsername())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .detailedAddress(address != null? address.getDetailedAddress() :  null)
                .build();
        return dto;
    }

    //현재 비밀번호 떤지기
    public static PasswordDto passwordToDto(Member member) {
        PasswordDto dto = PasswordDto.builder()
                .currentPassword(member.getPassword())
                .build();
        return dto;
    }

    public void updateInfo(String username, String phoneNumber, Address address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
