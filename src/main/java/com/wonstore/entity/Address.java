package com.wonstore.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String province; // 도
    private String city; // 시
    private String district; // 구
    private String detailedAddress; // 상세주소


    }

