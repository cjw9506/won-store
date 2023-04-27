package com.wonstore.service;

import com.wonstore.entity.ChargePoint;

import java.util.List;

public interface ChargePointService {

    Long charge(ChargePoint chargePoint);

    ChargePoint findOne(Long id);

    List<ChargePoint> findAll();
}
