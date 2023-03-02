package com.easytask.easytask.src.task.repository;

import com.easytask.easytask.common.BaseEntity;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskUserMappingRepository extends JpaRepository<TaskUserMapping, Long> {

    List<TaskUserMapping> findAllByTaskIdAndState(Long taskId, BaseEntity.State active);
}
