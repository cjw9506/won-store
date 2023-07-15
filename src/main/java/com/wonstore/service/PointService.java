package com.wonstore.service;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.point.PointRequest;
import com.wonstore.dto.apiDto.point.PointResponse;
import com.wonstore.entity.Member;
import com.wonstore.entity.Point;
import com.wonstore.exception.IdNotFound;
import com.wonstore.exception.PointNotFound;
import com.wonstore.repository.MemberRepository;
import com.wonstore.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    @Transactional //충전
    public Long charge(PointRequest request, MemberSession session) {

        Member member = memberRepository.findById(session.id).orElseThrow(IdNotFound::new);

        Point point = Point.builder()
                .amount(request.getPoint())
                .build();

        member.addChargePoint(point);
        pointRepository.save(point);

        return point.getId();
    }

    //전체조회
    public List<PointResponse> getList() {

        return pointRepository.findAll().stream()
                .map(p -> PointResponse.builder()
                        .memberId(p.getMember().getId())
                        .chargedPoint(p.getAmount())
                        .build())
                .collect(Collectors.toList());

    }

    //단건조회
    public PointResponse findOne(Long pointId) {

        Point point = pointRepository.findById(pointId).orElseThrow(PointNotFound::new);

        PointResponse response = PointResponse.builder()
                .memberId(point.getMember().getId())
                .chargedPoint(point.getAmount())
                .build();

        return response;
    }

}
