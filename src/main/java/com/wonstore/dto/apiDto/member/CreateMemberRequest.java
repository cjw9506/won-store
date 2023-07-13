package com.wonstore.dto.apiDto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "이름을 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "연락처를 입력하세요.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력하세요.")
    private String address;

    @Builder
    public CreateMemberRequest(String email, String username, String password, String phoneNumber, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
