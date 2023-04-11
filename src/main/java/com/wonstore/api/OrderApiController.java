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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderServiceImpl orderService;

    @PostMapping("/api/orders") //주문하기
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest request) throws NotEnoughException {
        Long id = orderService.order(request.getMemberId(), request.getItemId(), request.getCount());

        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(id));
    }

    @DeleteMapping("/api/orders/{id}") //주문취소
    public void cancelOrder(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
    }

//    @GetMapping("/api/orders") //전체 조회 -- 쿼리 두개
//    public List<OrderDto> ordersV2() {
//
//        List<Order> orders = orderService.orderList();
//        List<OrderDto> result = orders.stream()
//                .map(o -> new OrderDto(o))
//                .collect(Collectors.toList());
//
//        return result;
//    }

    @GetMapping("/api/orders") //전체 조회 -- 쿼리 하나 -- 페치조인 최적화 조회
    public Result orders() {
        List<Order> orders = orderService.findAllOrdersWithMemberAndDelivery();
        List<OrderListDto> collect = orders.stream()
                .map(o -> new OrderListDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/orders/{id}") //단건 조회
    public Result findOneOrder(@PathVariable("id") Long id) {
        Order findOne = orderService.findOneById(id);
        return new Result(findOne);
    }


}
