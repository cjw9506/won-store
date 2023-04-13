package com.wonstore.service;

import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Item;
import com.wonstore.repository.ChargePointRepository;
import com.wonstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChargePointServiceImpl implements ChargePointService{

    private final ChargePointRepository chargePointRepository;
    private final MemberRepository memberRepository;

    @Transactional //충전
    public Long charge(ChargePoint chargePoint) {
        chargePointRepository.save(chargePoint);
        return chargePoint.getId();
    }

    //단건조회
    public ChargePoint findOne(Long id) {
        ChargePoint chargePoint = chargePointRepository.findById(id).get();
        return chargePoint;
    }

    //전체조회
    public List<ChargePoint> findAll() {
        return chargePointRepository.findAll();
    }


}
