package com.wonstore.dto.apiDto.order;

import com.wonstore.entity.Order;
import com.wonstore.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OrderListDto {
    private Long id;
    private String userId;
    private List<String> itemNames;

    public OrderListDto(Order order) {
        id = order.getId();
        userId = order.getMember().getUserId();
        itemNames = order.getOrderItems().stream()
                .map(orderItem -> orderItem.getItem().getItemName())
                .collect(Collectors.toList());
    }
}
