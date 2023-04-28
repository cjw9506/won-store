package com.wonstore.dto.apiDto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    private String itemName;
    private int itemPrice;
    private int itemQuantity;
    private String itemDetail;
    private Long memberId;
    private Long categoryId;
}
