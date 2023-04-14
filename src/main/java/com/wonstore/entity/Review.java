package com.wonstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review { //member - 일대다, item - 일대다

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static Review createReview(Member member, Item item, String title, String content) {
        Review review = Review.builder()
                .title(title)
                .content(content)
                .member(member)
                .item(item)
                .build();
        member.addReview(review);
//        for (Review memberReview : member.getReviews()) {
//            System.out.println(memberReview.title);
//        }
        return review;
    }

    public void updateReview(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
