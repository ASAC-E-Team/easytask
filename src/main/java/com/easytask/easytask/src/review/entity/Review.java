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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratingId")
    private Rating rating;
    private String context;
    @Column(name = "createAt")
    private LocalDateTime localDateTime;

    public Review(Task task, TaskUserMapping taskUserMapping, Rating rating, String context) {
        this.task = task;
        this.taskUserMapping = taskUserMapping;
        this.rating = rating;
        this.context = context;
    }

}
