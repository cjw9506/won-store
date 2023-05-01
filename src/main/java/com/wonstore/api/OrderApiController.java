package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.order.*;
import com.wonstore.entity.Order;
import com.wonstore.exception.NotEnoughException;
import com.wonstore.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderServiceImpl orderService;

    @PostMapping("/api/orders/new") //주문하기
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest request) throws NotEnoughException {
        Long id = orderService.order(request.getMemberId(), request.getItemId(), request.getCount());

        return ResponseEntity.created(URI.create("/api/orders/" + id)).body(new OrderResponse(id));
    }

    @PostMapping("/api/orders-cart/new")
    public ResponseEntity<OrderResponse> saveCartOrder(@RequestBody OrderCartRequest request) throws NotEnoughException{
        Long id = orderService.cartOrder(request.getCartId());
        return ResponseEntity.created(URI.create("/api/orders-cart/" + id)).body(new OrderResponse(id));
    }

    @GetMapping("/api/orders") //전체 조회 -- 쿼리 하나 -- 페치조인 최적화 조회
    public Result orders() {
        List<Order> orders = orderService.findAllOrdersWithMemberAndDelivery();
        List<OrderListDto> collect = orders.stream()
                .map(o -> new OrderListDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/orders/{orderId}") //단건 조회
    public OrderDto findOneOrder(@PathVariable("orderId") Long orderId) {
        Order findOne = orderService.findOneById(orderId);
        OrderDto orderDto = new OrderDto(orderId,  findOne.getMember().getId(),
                findOne.getOrderItems().get(0).getItem().getId(), findOne.getOrderItems().get(0).getCount());
        return orderDto;
    }

    @DeleteMapping("/api/orders/{orderId}") //주문취소
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "주문번호 " + orderId + "번이 주문취소되었습니다.");

        return ResponseEntity.ok(response);
    }

}
