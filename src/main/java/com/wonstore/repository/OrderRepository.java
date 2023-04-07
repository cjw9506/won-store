package com.wonstore.repository;

import com.wonstore.entity.Member;
import com.wonstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.member m join fetch o.delivery d ")
    List<Order> findAllWithMemberDelivery();
}
