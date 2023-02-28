package com.easytask.easytask.src.task;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.src.task.dto.request.RelatedAbilityRequestDto;
import com.easytask.easytask.src.task.dto.response.RelatedAbilityResponseDto;
import com.easytask.easytask.src.task.dto.response.TaskResponseDto;
import com.easytask.easytask.src.task.dto.request.TaskRequestDto;
import com.easytask.easytask.src.task.dto.response.TaskIdResponseDto;
import com.easytask.easytask.src.task.entity.RelatedAbility;
import com.easytask.easytask.src.task.entity.Task;
import com.easytask.easytask.src.task.repository.RelatedAbilityRepository;
import com.easytask.easytask.src.task.repository.TaskRepository;
import com.easytask.easytask.src.user.UserRepository;
import com.easytask.easytask.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.easytask.easytask.common.BaseEntity.State.ACTIVE;
import static com.easytask.easytask.common.response.BaseResponseStatus.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final RelatedAbilityRepository relatedAbilityRepository;
    private final UserRepository userRepository;

    public TaskIdResponseDto createTask(Long customerId, TaskRequestDto taskRequestDto) {
        User customer = userRepository.findByIdAndState(customerId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        try {
            Task task = Task.builder()
                    .customer(customer)
                    .taskName(taskRequestDto.getTaskName())
                    .details(taskRequestDto.getDetails())
                    .categoryBig(taskRequestDto.getCategoryBig())
                    .categorySmall(taskRequestDto.getCategorySmall())
                    .numberOfIrumi(taskRequestDto.getNumberOfIrumi())
                    .build();

            taskRepository.save(task);

            return new TaskIdResponseDto(task);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public TaskResponseDto getTask(Long taskId) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));
        return new TaskResponseDto(task);
    }

    public TaskIdResponseDto updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            task.updateTask(taskRequestDto);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }

        return new TaskIdResponseDto(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            task.deleteTask();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public RelatedAbilityResponseDto postRelatedAbility(
            Long taskId, RelatedAbilityRequestDto relatedAbilityRequestDto) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TASK));

        try {
            RelatedAbility relatedAbility = RelatedAbility.builder()
                    .task(task)
                    .categoryBig(relatedAbilityRequestDto.getCategoryBig())
                    .categorySmall(relatedAbilityRequestDto.getCategorySmall())
                    .build();
            relatedAbilityRepository.save(relatedAbility);
            return new RelatedAbilityResponseDto(relatedAbility);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }

    public void deleteRelatedAbility(Long relatedAbilityId) {
        RelatedAbility relatedAbility = relatedAbilityRepository.findByIdAndState(relatedAbilityId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_ABILITY));
        try {
            relatedAbility.deleteRelatedAbility();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }
}
