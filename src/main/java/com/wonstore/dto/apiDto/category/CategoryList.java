package com.wonstore.dto.apiDto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryList {

    private Long id;
    private String name;
    private Long parentId;
}
