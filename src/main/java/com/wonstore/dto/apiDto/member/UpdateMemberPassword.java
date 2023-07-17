package com.wonstore.dto.apiDto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberPassword {

    private String currentPassword;
    private String changePassword;
    private String verifyPassword;

    @Builder
    public UpdateMemberPassword(String currentPassword, String changePassword, String verifyPassword) {
        this.currentPassword = currentPassword;
        this.changePassword = changePassword;
        this.verifyPassword = verifyPassword;
    }
}
