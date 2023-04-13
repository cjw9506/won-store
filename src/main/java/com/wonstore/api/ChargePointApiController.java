package com.wonstore.api;

import com.wonstore.dto.apiDto.Result;
import com.wonstore.dto.apiDto.charge.ChargeDto;
import com.wonstore.dto.apiDto.charge.ChargeRequest;
import com.wonstore.dto.apiDto.charge.ChargeResponse;
import com.wonstore.entity.ChargePoint;
import com.wonstore.entity.Member;
import com.wonstore.service.ChargePointServiceImpl;
import com.wonstore.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChargePointApiController { //충전내역 조회 추가할 것

    private final ChargePointServiceImpl chargePointService;
    private final MemberServiceImpl memberService;

    @PostMapping("/api/charge")
    public ResponseEntity<ChargeResponse> charge(@RequestBody ChargeRequest request) {
        Member member = memberService.findOne(request.getMemberId());

        ChargePoint chargePoint = ChargePoint.builder()
                .member(member)
                .amount(request.getChargePoint())
                .chargeDate(LocalDateTime.now())
                .build();

        member.addChargePoint(chargePoint);

        Long chargeId = chargePointService.charge(chargePoint);

        return ResponseEntity.created(URI.create("/api/charge/" + request.getMemberId()))
                .body(new ChargeResponse(request.getMemberId(), request.getChargePoint()));
    }

    @GetMapping("/api/charge")
    public Result charges() {
        List<ChargePoint> chargePoints = chargePointService.findAll();
        List<ChargeDto> collect = chargePoints.stream().map(c -> new ChargeDto(c.getId(), c.getMember().getId(), c.getAmount()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/charge/{id}")
    public ChargeDto charge(@PathVariable("id") Long id) {
        ChargePoint charge = chargePointService.findOne(id);
        ChargeDto chargeDto = new ChargeDto(id, charge.getMember().getId(), charge.getAmount());
        return chargeDto;
    }

}
