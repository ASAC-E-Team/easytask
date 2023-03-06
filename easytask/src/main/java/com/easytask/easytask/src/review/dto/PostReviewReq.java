package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.Review.Recommend;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReviewReq {

    private Long task;
    private Long taskUserMapping;
    private String context;
    private Recommend recommend;

}
