package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review.Recommend;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostReviewRes {
    private Long task;
    private Long taskUserMapping;
    private List<PostRatingRes> ratingList = new ArrayList<>();
    private String context;
    private Recommend recommend;

    public PostReviewRes(Review review) {
        this.task = review.getTask().getId();
        this.taskUserMapping = review.getTaskUserMapping().getId();
        this.context = review.getContext();
        this.recommend = review.getRecommend();
    }

    public void addPostRatingRes(PostRatingRes postRatingRes) {
        ratingList.add(postRatingRes);
    }

}
