package com.wonstore.dto.apiDto.order;

import com.wonstore.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {

    private Long memberId;
    private Long itemId;
    private int count;
}
