package com.wonstore.api;

import com.wonstore.config.data.MemberSession;
import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.point.PointDto;
import com.wonstore.dto.apiDto.point.PointRequest;
import com.wonstore.dto.apiDto.point.PointResponse;
import com.wonstore.entity.Point;
import com.wonstore.service.PointService;
import com.wonstore.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointApiController { //충전내역 조회 추가할 것

    private final PointService pointService;

    @PostMapping("/api/charge")
    public Long charge(@RequestBody PointRequest request, MemberSession session) {

        Long pointId = pointService.charge(request, session);

        return pointId;
    }

    @GetMapping("/api/charge")
    public List<PointResponse> getPoints(MemberSession session) {
        return pointService.getList();
    }

    @GetMapping("/api/charge/{pointId}")
    public PointResponse getPoint(@PathVariable Long pointId,
                                  MemberSession session) {
        return pointService.findOne(pointId);
    }

}
