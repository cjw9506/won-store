package com.wonstore.service;

import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.entity.Review;
import com.wonstore.repository.ItemRepository;
import com.wonstore.repository.MemberRepository;
import com.wonstore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional //리뷰 생성
    public Review createReview(Long memberId, Long itemId, String title, String content) {

        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();
        Review review = Review.createReview(member, item, title, content);
        reviewRepository.save(review);
        return review;
    }

    @Transactional //리뷰 수정
    public void updateReview(Long reviewId, String title, String content) {
        Review review = reviewRepository.findById(reviewId).get();
        review.updateReview(title, content);
    }

    public List<Review> findReviews() {
        List<Review> all = reviewRepository.findAll();
        return all;
    }

    public Review findReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        return review;
    }

    public List<Review> findMemberReview(Long memberId) {
        return reviewRepository.findByMemberId(memberId);
    }
}
