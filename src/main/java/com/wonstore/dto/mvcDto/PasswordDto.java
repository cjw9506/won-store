package com.wonstore.dto.mvcDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PasswordDto {

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;

}
