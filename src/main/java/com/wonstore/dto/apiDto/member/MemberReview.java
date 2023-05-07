package com.wonstore.dto.apiDto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReview {

    private Long itemId;
    private String title;
    private String content;
}
