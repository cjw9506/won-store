package com.wonstore.dto.apiDto.review;

import lombok.Getter;

@Getter
public class ReviewRequest {

    private Long memberId;
    private Long itemId;
    private String title;
    private String content;
}
