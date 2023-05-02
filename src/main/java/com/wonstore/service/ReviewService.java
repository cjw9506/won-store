package com.wonstore.service;

import com.wonstore.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(Long memberId, Long itemId, String title, String content);

    void updateReview(Long reviewId, String title, String content);

    List<Review> findReviews();

    Review findReview(Long reviewId);

    void deleteReview(Long reviewId);
}
