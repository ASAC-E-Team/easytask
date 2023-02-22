package com.easytask.easytask.src.user;

import com.easytask.easytask.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
