package com.easytask.easytask.src.task;

import com.easytask.easytask.src.user.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager entityManager;

    public void 업무_생성() {


    }
}