package com.wonstore.dto.mvcDto;

import com.wonstore.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class OrderDto {

    //회원 아이디
    private String memberName;

    //회원 이메일
    private String memberEmail;

    //회원 핸드폰번호
    private String memberPhoneNumber;

    //회원 주소
    private Address address;

    //주문할 상품 이름
    private String itemName;

    //주문할 상품 가격
    private Integer itemPrice;

    //상품 총 갯수
    private Integer itemQuantity;

    //주문수량
    private Integer count;

    //총 가격
    private Integer totalPrice;



}
