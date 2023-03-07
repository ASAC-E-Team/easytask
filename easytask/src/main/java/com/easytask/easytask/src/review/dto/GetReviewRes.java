package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import lombok.Getter;

import java.util.List;

@Getter
public class GetReviewRes {

    private Task task;
    private TaskUserMapping taskUserMapping;
    private List<Rating> ratingList;
    private String context;

    public GetReviewRes(Review review) {
        task = review.getTask();
        taskUserMapping = review.getTaskUserMapping();
        ratingList = review.getRatingList();
        context = review.getContext();
    }


}
