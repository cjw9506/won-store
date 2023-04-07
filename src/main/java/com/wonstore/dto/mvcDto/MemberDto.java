package com.wonstore.dto.mvcDto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberDto {


    @Size(min = 6, max = 16, message = "아이디는 필수이며, 최소 6자 이상, 최대 16자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email
    private String email;

    private String username;

    @Size(min = 8, max = 16, message = "비밀번호는 8~16자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$", message = "비밀번호는 숫자와 문자의 조합으로 입력해주세요.")
    private String password;

    private String phoneNumber;

    private String detailedAddress;







}
