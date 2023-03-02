package com.easytask.easytask.src.user;

import com.easytask.easytask.src.user.entity.PossibleTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossibleTaskRepository extends JpaRepository<PossibleTask, Long> {
}
