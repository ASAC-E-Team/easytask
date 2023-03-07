package com.easytask.easytask.src.task.dto;

import com.easytask.easytask.src.task.entity.Task;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class PostRelatedAbilityReq {
    private Long task;
    private String categoryBig;
    private String categorySmall;

}
