package com.wonstore.dto.apiDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Result<T> {
    private T data;
}
