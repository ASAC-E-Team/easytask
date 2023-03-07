package com.easytask.easytask.src.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRatingReq {
    private Long personalAbility;
    private Long relatedAbility;
    private int relatedAbilityRating;
    private int personalAbilityRating;

}
