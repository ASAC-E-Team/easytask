package com.easytask.easytask.src.task.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskPageResponseDto {
    private Integer nowPage;

    private Integer totalPages;

    private Long totalElements;

    private Integer size;

    private Boolean hasNext;

    private Boolean hasPrevious;

    private List<TaskResponseDto> taskList;

    public TaskPageResponseDto(Page<TaskResponseDto> page) {
        this.nowPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
        this.taskList = page.getContent();
    }
}
