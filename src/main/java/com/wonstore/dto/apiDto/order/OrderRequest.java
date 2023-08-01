package com.wonstore.dto.apiDto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderRequest {

    private Long itemId;
    private int itemCount;

    @Builder
    public OrderRequest(Long itemId, int itemCount) {
        this.itemId = itemId;
        this.itemCount = itemCount;
    }
}
