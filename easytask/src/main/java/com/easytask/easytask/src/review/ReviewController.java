package com.easytask.easytask.src.review;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.exception.BaseExceptionHandler;
import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.common.response.BaseResponseStatus;
import com.easytask.easytask.src.review.dto.GetReviewRes;
import com.easytask.easytask.src.review.dto.PostPersonalAbilityReq;
import com.easytask.easytask.src.review.dto.PostRequestRatingDto;
import com.easytask.easytask.src.review.dto.PostRequestReviewDto;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Rating;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.PersonalAbilityRepository;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.task.PostTaskReq;
import com.easytask.easytask.src.task.RelatedAbilityRepository;
import com.easytask.easytask.src.task.TaskRepository;
import com.easytask.easytask.src.task.TaskUserMappingRepository;
import com.easytask.easytask.src.task.dto.PostTaskUserMappingReq;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.user.UserRepository;
import com.easytask.easytask.src.user.UserService;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RelatedAbilityRepository relatedAbilityRepository;
    private final TaskUserMappingRepository taskUserMappingRepository;
    private final UserService userService;
    private final PersonalAbilityRepository personalAbilityRepository;

    @ResponseBody
    @PostMapping("")
    public BaseResponse<Review> createReview(@RequestBody PostRequestReviewDto postRequestReviewDto) {
        Long reviewId = reviewService.createReview(postRequestReviewDto);
        Review review = reviewRepository.findOne(reviewId);

        return new BaseResponse<>(review);
    }

    @ResponseBody
    @GetMapping("/{reviewId}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable("reviewId") Long reviewId) {
        GetReviewRes review = reviewService.getReview(reviewId);
        return new BaseResponse<>(review);
    }

    @ResponseBody
    @GetMapping("/lists/users/{userId}")
    public BaseResponse<List<Review>> getReviewsByUserId(@PathVariable("userId") Long userId) {
        List<Review> reviewList = reviewService.getReviewsByUserId(userId);
        return new BaseResponse<>(reviewList);
    }

    @ResponseBody
    @GetMapping("/lists/irumies/{irumiId}")
    public BaseResponse<List<Review>> getReviewsByIrumiId(@PathVariable("irumiId") Long irumiId) {
        List<Review> reviewList = reviewService.getReviewsByIrumiId(irumiId);

        return new BaseResponse<>(reviewList);
    }

    @ResponseBody
    @PostMapping("/add/rating/{reviewId}")
    public BaseResponse<Review> addRatingsOfReview(@PathVariable("reviewId") Long reviewId,
                                                         @RequestBody List<PostRequestRatingDto> postRequestRatingDtoList) {
        Review review = reviewService.addRatingsOfReview(reviewId, postRequestRatingDtoList);

        return new BaseResponse<>(review);
    }

//    @GetMapping("/rating/{reviewId}")
//    public BaseResponse<Rating> getRatingByReviewId(@PathVariable("reviewId") Long reviewId) {
//
//    }

    @ResponseBody
    @PostMapping("/personal")
    public BaseResponse<Optional<PersonalAbility>> createPersonalAbility(@RequestBody PostPersonalAbilityReq postPersonalAbilityReq) {
       Long personalAbilityId = reviewService.createPersonalAbility(postPersonalAbilityReq);
       Optional<PersonalAbility> personalAbility = personalAbilityRepository.findById(personalAbilityId);
       return new BaseResponse<>(personalAbility);
    }

    @ResponseBody
    @PostMapping("/relatedability")
    public RelatedAbility createRelatedAbility(@RequestBody RelatedAbility relatedAbility) {
        Task task = taskRepository.findById(relatedAbility.getTask().getId())
                .orElseThrow();

        RelatedAbility relatedAbility1 = new RelatedAbility(task,relatedAbility.getCategoryBig(), relatedAbility.getCategorySmall());

        relatedAbilityRepository.save(relatedAbility1);

        return relatedAbility1;
    }

    @ResponseBody
    @PostMapping("/user")
    public BaseResponse<User> createUser(@RequestBody User user) {
        Long userId = userService.createUser(user);
        return new BaseResponse<>(user);
    }

    @ResponseBody
    @PostMapping("/task")
    public BaseResponse<Task> createTask(@RequestBody PostTaskReq postTaskReq) {
        Task task = postTaskReq.toEntity(postTaskReq);
        User user = userRepository.findOne(postTaskReq.getCustomer());
        task.setCustomer(user);
        taskRepository.save(task);
        return new BaseResponse<>(task);
    }

    @ResponseBody
    @PostMapping("/taskusermapping")
    public TaskUserMapping createTaskUserMapping(@RequestBody PostTaskUserMappingReq postTaskUserMappingReq) {
        Task task = taskRepository.findById(postTaskUserMappingReq.getTask())
               .orElseThrow(() -> new BaseException(BaseResponseStatus.UNEXPECTED_ERROR));
        User user = userRepository.findOne(postTaskUserMappingReq.getIrumi());

        Hibernate.initialize(task);
        Hibernate.initialize(user);

        TaskUserMapping mapping = new TaskUserMapping(user,task);
        taskUserMappingRepository.save(mapping);

//        taskUserMappingRepository.save(taskUserMapping);

        return mapping;
    }

    @GetMapping("/{reviewId}/average")
    public BaseResponse<List<Double>> getAverageRating(@PathVariable Long reviewId) {
        List<Double> list = reviewService.getAverageRating(reviewId);
        return new BaseResponse<>(list);
    }


}
