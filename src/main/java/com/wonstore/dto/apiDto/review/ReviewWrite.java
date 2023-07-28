package com.wonstore.dto.apiDto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewWrite {

    private String title;
    private String content;

    @Builder
    public ReviewWrite(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
