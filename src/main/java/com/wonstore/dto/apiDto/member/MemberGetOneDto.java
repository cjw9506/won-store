package com.wonstore.dto.apiDto.member;


import com.wonstore.entity.Address;
import lombok.*;

@Getter
public class MemberGetOneDto {

    private String email;
    private String username;
    private String phoneNumber;
    private String address;
    private int currentPoint;

    @Builder
    public MemberGetOneDto(String email, String username, String phoneNumber, String address, int currentPoint) {
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.currentPoint = currentPoint;
    }
}
