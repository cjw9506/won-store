package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int amount;

    private LocalDateTime chargeDate;

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public Point(int amount) {
        this.amount = amount;
        this.chargeDate = LocalDateTime.now();
    }
}
