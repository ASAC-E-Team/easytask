package com.easytask.easytask.src.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskRequestDto {
    private String taskName;
    private String details;
    private String categoryBig;
    private String categorySmall;
    private Integer numberOfIrumi;
}
