package com.wonstore.dto.apiDto.category;

import lombok.Getter;

@Getter
public class ChildRequest {

    private String name;
    private Long parentId;
}
