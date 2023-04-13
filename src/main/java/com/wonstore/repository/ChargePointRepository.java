package com.wonstore.repository;

import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {

}
