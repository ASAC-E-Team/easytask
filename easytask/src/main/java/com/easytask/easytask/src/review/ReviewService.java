package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.dto.*;
import com.easytask.easytask.src.review.dto.request.PersonalAbilityRequestDto;
import com.easytask.easytask.src.review.dto.request.ReviewRequestDto;
import com.easytask.easytask.src.review.dto.response.PersonalAbilityRatingResponseDto;
import com.easytask.easytask.src.review.dto.response.RatingResponseDto;
import com.easytask.easytask.src.review.dto.response.RelatedAbilityRatingResponseDto;
import com.easytask.easytask.src.review.dto.response.ReviewResponseDto;
import com.easytask.easytask.src.review.entity.*;
import com.easytask.easytask.src.review.repository.*;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.repository.TaskRepository;
import com.easytask.easytask.src.user.entity.User;
import com.easytask.easytask.src.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.easytask.easytask.common.BaseEntity.State.ACTIVE;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private final PersonalAbilityRepository personalAbilityRepository;
    private final PersonalAbilityRatingRepository personalAbilityRatingRepository;
    private final RelatedAbilityRatingRepository relatedAbilityRatingRepository;
    private final RatingRepository ratingRepository;

    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto) {

        Task task = taskRepository.findById(reviewRequestDto.getTask())
                .orElseThrow();

        User irumi = userRepository.findById(reviewRequestDto.getIrumi())
                .orElseThrow();

        Review review = Review.builder()
                .task(task)
                .irumi(irumi)
                .context(reviewRequestDto.getContext())
                .recommend(reviewRequestDto.getRecommend())
                .build();

        reviewRepository.save(review);

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);

        return reviewResponseDto;
    }

    public ReviewResponseDto addRatingsOfReview(Long reviewId) {
        Random random = new Random();
        Review review = reviewRepository.findOne(reviewId);
        Rating rating = new Rating(review);
        ratingRepository.save(rating);
        RatingResponseDto ratingResponseDto = new RatingResponseDto();
        Task task = review.getTask();
        List<RelatedAbility> relatedAbilityList = task.getRelatedAbilityList();


        for (RelatedAbility relatedAbility : relatedAbilityList) {

            int score = random.nextInt(1) + 1;

            RelatedAbilityRating relatedAbilityRating = RelatedAbilityRating.builder()
                    .relatedAbility(relatedAbility)
                    .rating(rating)
                    .relatedAbilityRating(score)
                    .build();

            relatedAbilityRatingRepository.save(relatedAbilityRating);
            rating.addRelatedAbilityRating(relatedAbilityRating);
            RelatedAbilityRatingResponseDto relatedAbilityRatingResponseDto = new RelatedAbilityRatingResponseDto(relatedAbilityRating);
            ratingResponseDto.addRelatedAbilityRatingResponseDto(relatedAbilityRatingResponseDto);
        }

        review.addRating(rating);

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
        reviewResponseDto.addRatingResponseDto(ratingResponseDto);

        return reviewResponseDto;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.getReviewsByUserIdAndState(userId, ACTIVE);

        List<ReviewResponseDto> list = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            Rating rating = review.getRating();
            RatingResponseDto ratingResponseDto = new RatingResponseDto(rating);
            reviewResponseDto.addRatingResponseDto(ratingResponseDto);
            list.add(reviewResponseDto);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewsByIrumiId(Long irumiId) {
        List<Review> reviewList = reviewRepository.getReviewsByIrumiIdAndState(irumiId, ACTIVE);

        List<ReviewResponseDto> list = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            Rating rating = review.getRating();
            RatingResponseDto ratingResponseDto = new RatingResponseDto(rating);
            reviewResponseDto.addRatingResponseDto(ratingResponseDto);
            list.add(reviewResponseDto);
        }

        return list;
    }


    public PersonalAbility createPersonalAbility(PersonalAbilityRequestDto personalAbilityRequestDto) {
        PersonalAbility personalAbility = personalAbilityRequestDto.toEntity(personalAbilityRequestDto);
        personalAbilityRepository.save(personalAbility);
        return personalAbility;
    }

    public RatingAverageResponseDto getRatingAverage(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        Rating rating = review.getRating();
        List<RelatedAbilityRating> relatedAbilityRatingList = rating.getRelatedAbilityRatingList();
        List<PersonalAbilityRating> personalAbilityRatingList = rating.getPersonalAbilityRatingList();
        RatingResponseDto ratingResponseDto = new RatingResponseDto();

        double personalAbilityRatingSum = 0;
        double relatedAbilityRatingSum = 0;

        for (RelatedAbilityRating relatedAbilityRating : relatedAbilityRatingList) {
            relatedAbilityRatingSum += relatedAbilityRating.getRelatedAbilityRating();
            RelatedAbilityRatingResponseDto relatedAbilityRatingResponseDto =
                    new RelatedAbilityRatingResponseDto(relatedAbilityRating);

            ratingResponseDto.addRelatedAbilityRatingResponseDto(relatedAbilityRatingResponseDto);
        }

        for (PersonalAbilityRating personalAbilityRating : personalAbilityRatingList) {
            personalAbilityRatingSum += personalAbilityRating.getPersonalAbilityRating();
            PersonalAbilityRatingResponseDto personalAbilityRatingResponseDto
                    = new PersonalAbilityRatingResponseDto(personalAbilityRating);

            ratingResponseDto.addPersonalAbilityRatingResponseDto(personalAbilityRatingResponseDto);
        }

        double personalAbilityRatingAvg = personalAbilityRatingSum / relatedAbilityRatingList.size();
        double relatedAbilityRatingAvg = relatedAbilityRatingSum / personalAbilityRatingList.size();

        RatingAverageResponseDto ratingAverageResponseDto = new RatingAverageResponseDto(ratingResponseDto
                ,relatedAbilityRatingAvg,personalAbilityRatingAvg);

        return ratingAverageResponseDto;
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findOne(reviewId);
        review.delete();
    }
}
