package com.easytask.easytask.src.review.entity;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskUserMappingId")
    private TaskUserMapping taskUserMapping;

    private Rating rating;
    @Column(name = "createAt")
    private LocalDateTime localDateTime;


}
