package com.wonstore.entity;

import com.wonstore.dto.apiDto.member.CreateMemberRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email; //회원 이메일

    private String username; //회원 이름

    private String password; //회원 비밀번호

    private String phoneNumber; //폰 번호

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Cart cart;

    @Embedded
    private Address address; //회원 주소

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChargePoint> point = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    private int currentPoint;

    public void updateCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    //연관관계 메서드 - chargePoint <-> member
    public void addChargePoint(ChargePoint chargePoint) {
        this.getPoint().add(chargePoint); //멤버의 point 리스트에 chargePoint 값 추가
        chargePoint.updateMember(this); //chargePoint 에 변경된 멤버값을 세팅
        calculateCurrentPoint(); //멤버의 현재포인트를 업데이트
    }

    private void calculateCurrentPoint() {
        int totalPoint = 0;
        for (ChargePoint p : point) {
            totalPoint += p.getAmount();
        }
        this.updateCurrentPoint(totalPoint);
    }

    //회원 포인트 사용
    public void usePoint(int point) {
        int currentPoint = this.getCurrentPoint() - point;
        this.updateCurrentPoint(currentPoint);
    }

    //회원 정보 수정
    public void updateInfo(String username, String phoneNumber, Address address) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    //패스워드 수정
    public void changePassword(String password) {
        this.password = password;
    }

    public void addPoint(int point) {
        this.currentPoint += point;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setMember(this);
    }

    @Builder //생성 메서드
    public Member(String email, String username, String password, String phoneNumber, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = new Address(address);
    }

}
