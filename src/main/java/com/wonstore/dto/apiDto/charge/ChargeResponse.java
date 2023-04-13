package com.wonstore.dto.apiDto.charge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ChargeResponse {
    private Long memberId;
    private int chargedPoint;

}
