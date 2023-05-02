package com.wonstore.service;

import com.wonstore.entity.Item;
import com.wonstore.entity.Member;
import com.wonstore.entity.Review;
import com.wonstore.repository.ItemRepository;
import com.wonstore.repository.MemberRepository;
import com.wonstore.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional //리뷰 생성
    @Override
    public Review createReview(Long memberId, Long itemId, String title, String content) {

        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();
        Review review = Review.createReview(member, item, title, content);
        Review save = reviewRepository.save(review);

        log.info("{} 상품의 {}번째 리뷰가 작성되었습니다.", save.getItem().getItemName(), save.getId());
        log.info("작성자 : {}", save.getMember().getUserId());

        return review;
    }

    @Transactional //리뷰 수정
    @Override
    public void updateReview(Long reviewId, String title, String content) {
        Review review = reviewRepository.findById(reviewId).get();
        review.updateReview(title, content);

        log.info("{} 상품의 {}번째 리뷰가 수정되었습니다.", review.getItem().getItemName(), review.getId());
        log.info("작성자 : {}", review.getMember().getUserId());

    }

    //전체 조회
    @Override
    public List<Review> findReviews() {
        List<Review> all = reviewRepository.findAll();
        return all;
    }

    //단건 조회
    @Override
    public Review findReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        return review;
    }

    //회원별 단건 조회
    public List<Review> findMemberReview(Long memberId) {
        return reviewRepository.findByMemberId(memberId);
    }

    @Transactional //리뷰 삭제
    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();

        log.info("{} 상품의 {}번째 리뷰가 삭제되었습니다.", review.getItem().getItemName(), review.getId());
        log.info("작성자 : {}", review.getMember().getUserId());

        reviewRepository.delete(review);
    }
}
