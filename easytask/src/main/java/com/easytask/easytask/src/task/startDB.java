package com.easytask.easytask.src.task;

import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.repository.RelatedAbilityRepository;
import com.easytask.easytask.src.task.repository.TaskRepository;
import com.easytask.easytask.src.user.repository.UserRepository;
import com.easytask.easytask.src.user.entity.PossibleTask;
import com.easytask.easytask.src.user.entity.TaskAbility;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class startDB {
    private final InitService initService;

    @PostConstruct
    public void initializer() {
        initService.initUser();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserRepository userRepository;
        private final TaskRepository taskRepository;
        private final RelatedAbilityRepository relatedAbilityRepository;
        private final EntityManager em;

        public void initUser() {
            User user1 = createUser("email@gmail.com", "홍길동", "password");
            User user2 = createUser("email2@gmail.com", "김철수", "password");
            User user3 = createUser("email3@gmail.com", "김영희", "password");
            User user4 = createUser("email4@gmail.com", "김영희", "password");
            List<User> userList = Arrays.asList(user1, user2, user3, user4);
            userRepository.saveAll(userList);

            PossibleTask ptUser1 = createPossibleTask(user1, "시장(자료)조사", "인터넷 자료 검색");
            PossibleTask pt2User1 = createPossibleTask(user1, "마케팅", "SNS 세팅 및 관리");
            TaskAbility ta1User1 = createTaskAbility(user1, "자료조사", "국내사이트");
            TaskAbility ta2User1 = createTaskAbility(user1, "자료조사", "논문검색");
            TaskAbility ta3User1 = createTaskAbility(user1, "기획력", "콘텐츠기획");
            TaskAbility ta4User1 = createTaskAbility(user1, "기획력", "영상기획");

            PossibleTask pt1User2 = createPossibleTask(user2, "시장(자료)조사", "인터넷 자료 검색");
            TaskAbility ta1User2 = createTaskAbility(user2, "자료조사", "국내사이트");
            TaskAbility ta2User2 = createTaskAbility(user2, "자료조사", "논문검색");

            PossibleTask pt1User3 = createPossibleTask(user3, "시장(자료)조사", "인터넷 자료 검색");
            TaskAbility ta1User3 = createTaskAbility(user3, "자료조사", "인터넷정보검색");
            TaskAbility ta2User3 = createTaskAbility(user3, "자료조사", "국내사이트");
            TaskAbility ta3User3 = createTaskAbility(user3, "자료조사", "논문검색");

            PossibleTask pt1User4 = createPossibleTask(user4, "통번역", "초벌 번역");
            PossibleTask pt2User4 = createPossibleTask(user4, "통번역", "검수");
            TaskAbility ta1User4 = createTaskAbility(user4, "자료조사", "인터넷정보검색");
            TaskAbility ta2User4 = createTaskAbility(user4, "자료조사", "국내사이트");
            TaskAbility ta3User4 = createTaskAbility(user4, "자료조사", "논문검색");
            TaskAbility ta4User4 = createTaskAbility(user4, "언어역량", "한국어");
            TaskAbility ta5User4 = createTaskAbility(user4, "언어역량", "영어");
            TaskAbility ta6User4 = createTaskAbility(user4, "언어역량", "중국어");

            List<PossibleTask> possibleTaskList = Arrays.asList(ptUser1, pt2User1, pt1User2, pt1User3, pt1User4, pt2User4);
            possibleTaskList.forEach(em::persist);
            List<TaskAbility> taskAbilityList = Arrays
                    .asList(ta1User1, ta2User1, ta3User1, ta4User1, ta1User2, ta2User2, ta1User3,
                            ta2User3, ta3User3, ta1User4, ta2User4, ta3User4, ta4User4, ta5User4, ta6User4);
            taskAbilityList.forEach(em::persist);

            Task task1 = Task.builder()
                    .customer(user2)
                    .taskName("식당 조사 [줌X]")
                    .details("식당 들을 조사하고 있습니다. 한식, 일식, 양식, 커피집 각 주제 별로 3개 씩 조사해서 \n 각 식당 별로 블로그 리뷰 링크 , 네이버 지도나 카카오 지도의 링크, 주소지 세가지 항목을 스크랩 해주시면 됩니다.")
                    .categoryBig("시장(자료)조사")
                    .categorySmall("인터넷 자료 검색")
                    .numberOfIrumi(2)
                    .build();
            taskRepository.save(task1);

            Task task2 = Task.builder()
                    .customer(user2)
                    .taskName("경제 조사")
                    .details("경제 조사 중입니다.")
                    .categoryBig("시장(자료)조사")
                    .categorySmall("인터넷 자료 검색")
                    .numberOfIrumi(1)
                    .build();
            taskRepository.save(task2);

            RelatedAbility relatedAbility11 = createRelatedAbility("자료조사", "인터넷정보검색", task1);
            RelatedAbility relatedAbility12 = createRelatedAbility("기획력", "콘텐츠기획", task1);
            RelatedAbility relatedAbility21 = createRelatedAbility("디자인", "웹퍼블리싱", task2);

            List<RelatedAbility> relatedAbilityList = Arrays.asList(relatedAbility11, relatedAbility12, relatedAbility21);
            relatedAbilityList.forEach(em::persist);
        }

        private RelatedAbility createRelatedAbility(String categoryBig, String categorySmall, Task task) {
            return RelatedAbility.builder()
                    .categoryBig(categoryBig)
                    .categorySmall(categorySmall)
                    .task(task)
                    .build();
        }

        private PossibleTask createPossibleTask(User user, String categoryBig, String categorySmall) {
            return PossibleTask.builder()
                    .user(user)
                    .categoryBig(categoryBig)
                    .categorySmall(categorySmall)
                    .build();
        }

        private TaskAbility createTaskAbility(User user, String categoryBig, String categorySmall) {
            return TaskAbility.builder()
                    .user(user)
                    .categoryBig(categoryBig)
                    .categorySmall(categorySmall)
                    .build();
        }

        private User createUser(String email, String name, String password) {
            return User.builder()
                    .email(email)
                    .name(name)
                    .password(password)
                    .build();
        }
    }
}