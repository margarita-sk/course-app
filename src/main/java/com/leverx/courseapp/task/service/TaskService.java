package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.model.Task;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
public interface TaskService {

    Task receiveTaskById(@Min(0) int courseId,@Min(0)  int taskId);

    Collection<Task> receiveAllTasksByCourse(@Min(0) int courseId);

    void addTask(@Min(0) int courseId,@Valid TaskDto taskDto);

    Task editTask(@Min(0) int courseId,@Valid TaskDto taskDto,@Min(0)  int taskId);

    void deleteTask(@Min(0) int courseId,@Min(0)  int taskId);
}
