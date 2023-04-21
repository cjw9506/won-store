package com.wonstore.dto.apiDto.member;

import lombok.Getter;

@Getter
public class UpdateMemberPassword {

    private String currentPassword;
    private String changePassword;
    private String verifyPassword;
}
