package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.model.Task;
import java.util.Collection;

public interface TaskService {

  Task receiveTaskById(int courseId, int taskId);

  Collection<Task> receiveAllTasksByCourse(int courseId);

  void addTask(int courseId, TaskDto taskDto);

  void editTask(int courseId, TaskDto taskDto, int taskId);

  void deleteTask(int courseId, int taskId);
}
