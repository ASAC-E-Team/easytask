package com.easytask.easytask.src.task.dto;

import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor
public class PostTaskUserMappingRes {

    private Long irumi;
    private Long task;

    public PostTaskUserMappingRes(TaskUserMapping taskUserMapping) {
        this.irumi = taskUserMapping.getIrumi().getId();
        this.task = taskUserMapping.getTask().getId();
    }
}
