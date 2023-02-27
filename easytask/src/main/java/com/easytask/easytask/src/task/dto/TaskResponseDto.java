package com.easytask.easytask.src.task.dto;

import com.easytask.easytask.src.task.entity.Task;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponseDto {
    private Long taskId;
    private String taskName;
    private String details;
    private String categoryBig;
    private String categorySmall;
    private Integer numberOfIrumi;
    private String matchingStatus;

    public TaskResponseDto(Task task) {
        this.taskId = task.getId();
        this.taskName = task.getTaskName();
        this.details = task.getDetails();
        this.categoryBig = task.getCategoryBig();
        this.categorySmall = task.getCategorySmall();
        this.numberOfIrumi = task.getNumberOfIrumi();
        this.matchingStatus = task.getMatchingStatus().name();
    }
}
