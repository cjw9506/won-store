package com.wonstore.dto.apiDto.point;

import lombok.*;

@Getter
@NoArgsConstructor
public class PointRequest {
    private Long memberId;
    private int point;

    @Builder
    public PointRequest(Long memberId, int point) {
        this.memberId = memberId;
        this.point = point;
    }
}
