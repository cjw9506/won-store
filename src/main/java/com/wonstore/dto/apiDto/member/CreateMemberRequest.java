package com.wonstore.dto.apiDto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberRequest {

    private String id;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;

}
