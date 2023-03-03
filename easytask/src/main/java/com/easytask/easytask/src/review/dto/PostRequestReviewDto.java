package com.easytask.easytask.src.review.dto;

import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;

public class PostRequestReviewDto {

    private Task task;
    private TaskUserMapping taskUserMapping;
    private String context;

    public Review toEntity() {
        return Review.builder()
                .task(this.task)
                .taskUserMapping(this.taskUserMapping)
                .context(this.context)
                .build();
    }
}
