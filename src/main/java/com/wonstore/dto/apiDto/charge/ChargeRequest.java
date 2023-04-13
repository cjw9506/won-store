package com.wonstore.dto.apiDto.charge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequest {
    private Long memberId;
    private int chargePoint;

}
