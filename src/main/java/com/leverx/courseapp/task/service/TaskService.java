package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.dto.TaskDtoShort;
import com.leverx.courseapp.task.model.Task;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TaskService {

  TaskDto receiveTaskById(int courseId, int taskId);

  Collection<TaskDtoShort> receiveAllTasksByCourse(int courseId);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void addTask(int courseId, TaskDto taskDto);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  TaskDto editTask(int courseId, TaskDto taskDto, int taskId);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void deleteTask(int courseId, int taskId);
}
