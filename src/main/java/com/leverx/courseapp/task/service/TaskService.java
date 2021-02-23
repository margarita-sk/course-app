package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.dto.TaskDtoShort;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TaskService {

  TaskDto receiveTaskById(int courseId, int taskId);

  Collection<TaskDtoShort> receiveAllTasksByCourse(int courseId);

  @PreAuthorize("hasAuthority('admins')")
  void addTask(int courseId, TaskDto taskDto);

  @PreAuthorize("hasAuthority('admins')")
  TaskDto editTask(int courseId, TaskDto taskDto, int taskId);

  @PreAuthorize("hasAuthority('admins')")
  void deleteTask(int courseId, int taskId);
}
