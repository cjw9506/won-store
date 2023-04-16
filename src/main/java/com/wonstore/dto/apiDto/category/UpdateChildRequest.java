package com.wonstore.dto.apiDto.category;

import lombok.Getter;

@Getter
public class UpdateChildRequest {

    private String name;
    private Long parentId;
    private Long newParentId;
}
