package com.wonstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MemberDto {

    private String username;
    private String password;

}
