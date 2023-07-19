package com.wonstore.dto.apiDto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Long id;
    private String itemName;
    private String itemDetail;
    private int price;
    private int stockQuantity;

    @Builder
    public ItemResponse(Long id, String itemName, String itemDetail, int price, int stockQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
