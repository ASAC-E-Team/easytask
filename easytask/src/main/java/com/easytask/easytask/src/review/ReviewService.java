package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void createReview(Review review) {
        reviewRepository.save(review);
    }


}
