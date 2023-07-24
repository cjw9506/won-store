package com.wonstore.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

//    private String city;
//    private String street;
//    private String zipcode;
//
//    public Address(String city, String street, String zipcode) {
//        this.city = city;
//        this.street = street;
//        this.zipcode = zipcode;
//    }

    private String detailedAddress; // 상세주소

    @Builder
    public Address(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }
}

