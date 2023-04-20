package com.wonstore.dto.apiDto.cart;

import lombok.Getter;

@Getter
public class CartAddRequest {

    private Long itemId;
    private int count;
}
