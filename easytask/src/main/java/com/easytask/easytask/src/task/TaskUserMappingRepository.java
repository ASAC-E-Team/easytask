package com.easytask.easytask.src.task;

import com.easytask.easytask.src.task.entity.TaskUserMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public interface TaskUserMappingRepository extends JpaRepository<TaskUserMapping, Long> {

    Optional<TaskUserMapping> findById(Long id);
}
