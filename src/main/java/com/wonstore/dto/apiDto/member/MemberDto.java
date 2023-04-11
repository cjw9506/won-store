package com.wonstore.dto.apiDto.member;


import com.wonstore.entity.Address;
import lombok.*;

@Getter
@AllArgsConstructor
public class MemberDto {

    private String id;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private Address address;
    private int currentPoint;
}
