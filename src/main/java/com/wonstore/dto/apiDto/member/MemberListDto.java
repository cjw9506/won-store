package com.wonstore.dto.apiDto.member;

import com.wonstore.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberListDto {

    private String email;
    private String username;
    private String phoneNumber;
    private Address address;
    private int currentPoint;

    @Builder
    public MemberListDto(String email, String username, String phoneNumber, Address address, int currentPoint) {
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.currentPoint = currentPoint;
    }
}
