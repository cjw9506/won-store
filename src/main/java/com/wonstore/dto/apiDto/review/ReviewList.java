package com.wonstore.dto.apiDto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewList {

    private Long id;
    private Long memberId;
    private Long itemId;
    private String title;
    private String content;
}
