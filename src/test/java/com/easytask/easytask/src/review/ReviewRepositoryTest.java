package com.easytask.easytask.src.review;

import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.user.UserController;
import com.easytask.easytask.src.user.UserRepository;
import com.easytask.easytask.src.user.UserService;
import com.easytask.easytask.src.user.entity.PossibleTask;
import com.easytask.easytask.src.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    EntityManager entityManager;



    @Test
    @Transactional
    @Commit
    public void 리뷰_생성() {
        //given
        User user = new User("whdghrkwhr12@naver.com", "최종호", "01085738206");
        userRepository.save(user);
        entityManager.flush();

//        TaskAbility taskAbility = new TaskAbility(user,"디자인", "편집 디자인");
//        entityManager.persist(taskAbility);
//        TaskAbility taskAbility2 = new TaskAbility(user, "기획력", "컨텐츠기획");
//        entityManager.persist(taskAbility2);
        PossibleTask possibleTask = new PossibleTask(user, "업무", "워드");
        entityManager.persist(possibleTask);
        entityManager.flush();

        //when
        List<PossibleTask> list = user.getPossibleTaskList();

        //then
        assertEquals(1, list.size());
    }
}