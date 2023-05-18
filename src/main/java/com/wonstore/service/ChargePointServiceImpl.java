package com.wonstore.service;

import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Item;
import com.wonstore.repository.ChargePointRepository;
import com.wonstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChargePointServiceImpl implements ChargePointService{

    private final ChargePointRepository chargePointRepository;
    private final MemberRepository memberRepository;

    @Transactional //충전
    @Override
    public Long charge(ChargePoint chargePoint) {
        ChargePoint charge = chargePointRepository.save(chargePoint);
        log.info("{}님이 {}포인트 충전하였습니다.", charge.getMember().getUserId(), charge.getAmount());
        return chargePoint.getId();
    }

    //단건조회
    @Override
    public ChargePoint findOne(Long id) {
        ChargePoint chargePoint = chargePointRepository.findById(id).get();
        return chargePoint;

    }

    //전체조회
    @Override
    public List<ChargePoint> findAll() {
        return chargePointRepository.findAll();
    }


}
