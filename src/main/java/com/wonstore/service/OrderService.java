package com.wonstore.service;

import com.wonstore.entity.Order;
import com.wonstore.exception.NotEnoughException;

import java.util.List;

public interface OrderService {

    //생성
    Long order(Long memberId, Long itemId, int count) throws NotEnoughException;

    //장바구니 주문
    Long cartOrder(Long cartId) throws NotEnoughException;

    //전체 조회
    List<Order> orderList();

    //단건 조회
    Order findOneById(Long orderId);

    //삭제
    void cancelOrder(Long orderId);

    //페치 조인 조회
    List<Order> findAllOrdersWithMemberAndDelivery();
}
