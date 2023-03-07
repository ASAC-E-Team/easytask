package com.easytask.easytask.src.task.dto.request;

import com.easytask.easytask.src.task.entity.Task;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
public class PostTaskReq {

    private Long customer;

    private String taskName;

    private String details;

    private String categoryBig;

    private String categorySmall;

    public Task toEntity(PostTaskReq postTaskReq) {
        return Task.builder()
                .taskName(postTaskReq.getTaskName())
                .categoryBig(postTaskReq.getCategoryBig())
                .categorySmall(postTaskReq.getCategorySmall())
                .details(postTaskReq.getDetails())
                .build();
    }

}
