package com.easytask.easytask.src.task;

import com.easytask.easytask.common.response.BaseResponse;
import com.easytask.easytask.src.task.dto.TaskResponseDto;
import com.easytask.easytask.src.task.dto.TaskRequestDto;
import com.easytask.easytask.src.task.dto.TaskIdResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/easytask/tasks")
public class TaskController {

    private final TaskService taskService;

    @ResponseBody
    @PostMapping("/customers/{customerId}")
    public BaseResponse<TaskIdResponseDto> createTask(
            @PathVariable("customerId") Long customerId, @RequestBody TaskRequestDto taskRequestDto) {
        TaskIdResponseDto taskIdResponseDto = taskService.createTask(customerId, taskRequestDto);
        return new BaseResponse<>(taskIdResponseDto);
    }

    @ResponseBody
    @GetMapping("{taskId}")
    public BaseResponse<TaskResponseDto> getTask(@PathVariable("taskId") Long taskId) {
        TaskResponseDto taskResponseDto = taskService.getTask(taskId);
        return new BaseResponse<>(taskResponseDto);
    }

    @ResponseBody
    @PutMapping("{taskId}")
    public BaseResponse<TaskIdResponseDto> updateTask(
            @PathVariable("taskId") Long taskId, @RequestBody TaskRequestDto taskRequestDto) {
        TaskIdResponseDto taskIdResponseDto = taskService.updateTask(taskId, taskRequestDto);
        return new BaseResponse<>(taskIdResponseDto);
    }

    @ResponseBody
    @DeleteMapping("{taskId}")
    public BaseResponse<String> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return new BaseResponse<>("업무가 삭제되었습니다.");
    }
}
