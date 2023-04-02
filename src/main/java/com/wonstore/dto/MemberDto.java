package com.wonstore.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberDto {

    @Size(min = 6, max = 16, message = "아이디는 필수이며, 최소 6자 이상, 최대 16자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email
    private String email;

    private String username;

    @Size(min = 8, max = 16, message = "")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$", message = "비밀번호는 숫자와 문자의 조합으로 8~16자 이내로 입력해주세요.")
    private String password;


    private String phoneNumber;

    private String detailedAddress;




}
