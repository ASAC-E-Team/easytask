package com.easytask.easytask.src.task;

import com.easytask.easytask.src.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final EntityManager entityManager;

    public void save(Task task) {
        entityManager.persist(task);
    }

    public Task findOne(Long taskId) {
        return entityManager.find(Task.class, taskId);
    }


}
