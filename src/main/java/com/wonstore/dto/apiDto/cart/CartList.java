package com.wonstore.dto.apiDto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartList {

    private Long cartId;
    private Long memberId;
    private List<Long> itemId;
    private List<Integer> count;
}
