package com.wonstore.dto.apiDto.point;

import lombok.*;

@Getter
@NoArgsConstructor
public class PointResponse {
    private Long memberId;
    private int chargedPoint;

    @Builder
    public PointResponse(Long memberId, int chargedPoint) {
        this.memberId = memberId;
        this.chargedPoint = chargedPoint;
    }
}
