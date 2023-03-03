package com.easytask.easytask.common.scheduler;

import com.easytask.easytask.src.task.entity.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Component
public class MatchingRequest {
    private List<Task> taskList = new ArrayList<>();
    private Integer scheduledIndex = 0;

    public void addTask(Task task) {
        taskList.add(task);
        log.info("add matchingRequest : {}", taskList.get(taskList.size()-1));
    }

    public Task getScheduledTask() {
        return taskList.get(scheduledIndex);
    }

    public void moveIndex() {
        this.scheduledIndex = (scheduledIndex + 1) % taskList.size();
    }
}
