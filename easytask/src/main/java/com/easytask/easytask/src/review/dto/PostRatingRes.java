package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
public class PostRatingRes {
    private Long review;
    private Long relatedAbility;
    private Long personalAbility;
    private int relatedAbilityRating;
    private int personalAbilityRating;

    public PostRatingRes(Rating rating) {
        this.review = rating.getReview().getId();
        this.relatedAbility = rating.getRelatedAbility().getId();
        this.personalAbility = rating.getPersonalAbility().getId();
        this.relatedAbilityRating = rating.getRelatedAbilityRating();
        this.personalAbilityRating = rating.getPersonalAbilityRating();
    }
}
