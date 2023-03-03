package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.src.task.entity.TaskMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMailRepository extends JpaRepository<TaskMail, Long> {
}
