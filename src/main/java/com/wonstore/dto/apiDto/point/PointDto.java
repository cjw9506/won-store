package com.wonstore.dto.apiDto.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {

    private Long id;
    private Long memberId;
    private int chargePoint;
}
