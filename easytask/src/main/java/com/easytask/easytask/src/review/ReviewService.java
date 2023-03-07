package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.dto.*;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.PersonalAbilityRepository;
import com.easytask.easytask.src.review.repository.RatingRepository;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.task.repository.RelatedAbilityRepository;
import com.easytask.easytask.src.task.repository.TaskRepository;
import com.easytask.easytask.src.task.repository.TaskUserMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TaskRepository taskRepository;
    private final TaskUserMappingRepository taskUserMappingRepository;
    private final PersonalAbilityRepository personalAbilityRepository;
    private final RelatedAbilityRepository relatedAbilityRepository;
    private final RatingRepository ratingRepository;

    public PostReviewRes createReview(PostReviewReq postReviewReq) {

        Task task = taskRepository.findById(postReviewReq.getTask())
                .orElseThrow();

        TaskUserMapping taskUserMapping = taskUserMappingRepository.findById(postReviewReq.getTaskUserMapping())
                .orElseThrow();

        Review review = Review.builder()
                .task(task)
                .taskUserMapping(taskUserMapping)
                .context(postReviewReq.getContext())
                .recommend(postReviewReq.getRecommend())
                .build();

        reviewRepository.save(review);

        PostReviewRes postReviewRes = new PostReviewRes(review);

        return postReviewRes;
    }

    public PostReviewRes addRatingsOfReview(Long reviewId, List<PostRatingReq> postRatingReqList) {
        Review review = reviewRepository.findOne(reviewId);
        PostReviewRes postReviewRes = new PostReviewRes(review);

        for (PostRatingReq postRatingReq : postRatingReqList) {
            PersonalAbility personalAbility = personalAbilityRepository.findById(postRatingReq.getPersonalAbility())
                    .orElseThrow();

            RelatedAbility relatedAbility = relatedAbilityRepository.findById(postRatingReq.getRelatedAbility())
                    .orElseThrow();

            Rating rating = Rating.builder()
                    .review(review)
                    .personalAbility(personalAbility)
                    .relatedAbility(relatedAbility)
                    .relatedAbilityRating(postRatingReq.getRelatedAbilityRating())
                    .personalAbilityRating(postRatingReq.getPersonalAbilityRating())
                    .build();

            ratingRepository.save(rating);
            PostRatingRes postRatingRes = new PostRatingRes(rating);
            postReviewRes.addPostRatingRes(postRatingRes);
        }

        return postReviewRes;
    }

    @Transactional(readOnly = true)
    public GetReviewRes getReview(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        return new GetReviewRes(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.getReviewsByUserId(userId);
        return reviewList;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByIrumiId(Long irumiId) {
        List<Review> reviewList = reviewRepository.getReviewsByIrumiId(irumiId);
        return reviewList;
    }

    public Long createPersonalAbility(PostPersonalAbilityReq postPersonalAbilityReq) {
        PersonalAbility personalAbility = postPersonalAbilityReq.toEntity(postPersonalAbilityReq);
        personalAbilityRepository.save(personalAbility);
        return personalAbility.getId();
    }

    public GetAverageRatingRes getAverageRating(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);

        List<Rating> ratingList = review.getRatingList();

        GetAverageRatingRes getAverageRatingRes = new GetAverageRatingRes();

        double personalAbilityRatingSum = 0;
        double relatedAbilityRatingSum = 0;

        for (Rating rating : ratingList) {
            RelatedAbilityList relatedAbilityList = new RelatedAbilityList(rating.getRelatedAbility().getCategorySmall()
                    ,rating.getRelatedAbilityRating());
            PersonalAbilityList personalAbilityList = new PersonalAbilityList(rating.getPersonalAbility().getPersonalAbility()
                    ,rating.getPersonalAbilityRating());

            getAverageRatingRes.getGetRelatedAbilitylists().add(relatedAbilityList);
            getAverageRatingRes.getGetPersonalAbilitylists().add(personalAbilityList);

            personalAbilityRatingSum += rating.getPersonalAbilityRating();
            relatedAbilityRatingSum += rating.getRelatedAbilityRating();
        }

        int ratingCount = ratingList.size();
        double personalAbilityRatingAvg = personalAbilityRatingSum / ratingCount;
        double relatedAbilityRatingAvg = relatedAbilityRatingSum / ratingCount;

        getAverageRatingRes.setPersonalAverage(personalAbilityRatingAvg);
        getAverageRatingRes.setRelatedAverage(relatedAbilityRatingAvg);

        return getAverageRatingRes;
    }
}
