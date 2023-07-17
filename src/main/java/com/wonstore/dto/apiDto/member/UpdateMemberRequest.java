package com.wonstore.dto.apiDto.member;

import lombok.*;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {

    private String username;
    private String phoneNumber;
    private String address;

    @Builder
    public UpdateMemberRequest(String username, String phoneNumber, String address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
