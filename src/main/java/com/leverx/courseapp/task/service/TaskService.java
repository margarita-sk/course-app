package com.leverx.courseapp.task.service;

import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.dto.TaskDtoShort;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;

public interface TaskService {

    TaskDto receiveTaskById(int courseId, int taskId);

    Collection<TaskDtoShort> receiveAllTasksByCourse(int courseId);

    void addTask(int courseId, TaskDto taskDto);

    TaskDto editTask(int courseId, TaskDto taskDto, int taskId);

    void deleteTask(int courseId, int taskId);
}
