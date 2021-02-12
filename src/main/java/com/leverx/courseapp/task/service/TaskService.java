package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.model.Task;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TaskService {

  Task receiveTaskById(int courseId, int taskId);

  Collection<Task> receiveAllTasksByCourse(int courseId);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void addTask(int courseId, TaskDto taskDto);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void editTask(int courseId, TaskDto taskDto, int taskId);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void deleteTask(int courseId, int taskId);
}
