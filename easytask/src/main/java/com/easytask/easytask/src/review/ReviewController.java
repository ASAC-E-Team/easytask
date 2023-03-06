package com.easytask.easytask.src.review;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.common.response.BaseResponseStatus;
import com.easytask.easytask.src.review.dto.*;
import com.easytask.easytask.src.review.entity.PersonalAbility;
import com.easytask.easytask.src.review.entity.Review;
import com.easytask.easytask.src.review.repository.PersonalAbilityRepository;
import com.easytask.easytask.src.review.repository.ReviewRepository;
import com.easytask.easytask.src.task.PostTaskReq;
import com.easytask.easytask.src.task.RelatedAbilityRepository;
import com.easytask.easytask.src.task.TaskRepository;
import com.easytask.easytask.src.task.TaskUserMappingRepository;
import com.easytask.easytask.src.task.dto.PostRelatedAbilityReq;
import com.easytask.easytask.src.task.dto.PostTaskUserMappingReq;
import com.easytask.easytask.src.task.dto.PostTaskUserMappingRes;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.entity.TaskUserMapping;
import com.easytask.easytask.src.user.UserRepository;
import com.easytask.easytask.src.user.UserService;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.easytask.easytask.common.response.BaseResponseStatus.FIND_ERROR;

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
    public BaseResponse<PostReviewRes> createReview(@RequestBody PostReviewReq postReviewReq) {
        PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
        return new BaseResponse<>(postReviewRes);
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
    public BaseResponse<PostReviewRes> addRatingsOfReview(@PathVariable("reviewId") Long reviewId,
                                                         @RequestBody List<PostRatingReq> postRatingReqList) {
        PostReviewRes postReviewRes = reviewService.addRatingsOfReview(reviewId, postRatingReqList);
        return new BaseResponse<>(postReviewRes);
    }

    @ResponseBody
    @PostMapping("/personal")
    public BaseResponse<Optional<PersonalAbility>> createPersonalAbility(@RequestBody PostPersonalAbilityReq postPersonalAbilityReq) {
       Long personalAbilityId = reviewService.createPersonalAbility(postPersonalAbilityReq);
       Optional<PersonalAbility> personalAbility = personalAbilityRepository.findById(personalAbilityId);
       return new BaseResponse<>(personalAbility);
    }

    @ResponseBody
    @PostMapping("/relatedability")
    public BaseResponse<RelatedAbility> createRelatedAbility(@RequestBody PostRelatedAbilityReq postRelatedAbilityReq) {
        Task task = taskRepository.findById(postRelatedAbilityReq.getTask())
                .orElseThrow();

        RelatedAbility relatedAbility = RelatedAbility.builder()
                .task(task)
                .categoryBig(postRelatedAbilityReq.getCategoryBig())
                .categorySmall(postRelatedAbilityReq.getCategorySmall())
                .build();

        relatedAbilityRepository.save(relatedAbility);

        return new BaseResponse<>(relatedAbility);
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
        User user = userRepository.findById(postTaskReq.getCustomer())
                .orElseThrow(() -> new BaseException(FIND_ERROR));
        task.setCustomer(user);
        taskRepository.save(task);
        return new BaseResponse<>(task);
    }

    @ResponseBody
    @PostMapping("/taskusermapping")
    public BaseResponse<PostTaskUserMappingRes> createTaskUserMapping(@RequestBody PostTaskUserMappingReq postTaskUserMappingReq) {
        Task task = taskRepository.findById(postTaskUserMappingReq.getTask())
               .orElseThrow(() -> new BaseException(BaseResponseStatus.UNEXPECTED_ERROR));
        User user = userRepository.findById(postTaskUserMappingReq.getIrumi())
                .orElseThrow(() -> new BaseException(FIND_ERROR));

        TaskUserMapping mapping = new TaskUserMapping(user,task);
        taskUserMappingRepository.save(mapping);


        PostTaskUserMappingRes postTaskUserMappingRes = new PostTaskUserMappingRes(mapping);

        return new BaseResponse<>(postTaskUserMappingRes);
    }

    @GetMapping("/{reviewId}/average")
    public BaseResponse<GetAverageRatingRes> getAverageRating(@PathVariable Long reviewId) {
        GetAverageRatingRes getAverageRatingRes = reviewService.getAverageRating(reviewId);
        return new BaseResponse<>(getAverageRatingRes);
    }


}
