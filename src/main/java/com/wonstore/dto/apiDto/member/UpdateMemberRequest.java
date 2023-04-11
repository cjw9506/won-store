package com.wonstore.dto.apiDto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {

    private String username;
    private String phoneNumber;
    private String address;
}
