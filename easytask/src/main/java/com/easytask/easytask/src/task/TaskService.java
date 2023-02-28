package com.easytask.easytask.src.task;

import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.src.task.dto.response.TaskResponseDto;
import com.easytask.easytask.src.task.dto.request.TaskRequestDto;
import com.easytask.easytask.src.task.dto.response.TaskIdResponseDto;
import com.easytask.easytask.src.task.entity.Task;
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
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));
        return new TaskResponseDto(task);
    }

    public TaskIdResponseDto updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        try {
            task.updateTask(taskRequestDto);
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }

        return new TaskIdResponseDto(task);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndState(taskId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        try {
            task.deleteTask();
        } catch (Exception exception) {
            throw new BaseException(DB_CONNECTION_ERROR);
        }
    }
}
