package com.wonstore.api;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.order.OrderRequest;
import com.wonstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/orders/new")
    public Long createOrder(@RequestBody OrderRequest request,
                            MemberSession session) {
        Long orderId = orderService.order(request, session);

        return orderId;
    }

    @DeleteMapping("/api/orders/{orderId}")
    public Long cancelOrder(@PathVariable Long orderId,
                            MemberSession session) {
        Long id = orderService.cancel(orderId);

        return id;
    }
}
