package com.wonstore.dto.apiDto.charge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargeDto {

    private Long id;
    private Long memberId;
    private int chargePoint;
}
