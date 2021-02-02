package com.leverx.courseapp.task.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.task.model.Task;

import java.util.Collection;

public interface TaskService {

    Task receiveTaskById(Course course, int id);

    Collection<Task> receiveAllTasksByCourse(Course course);

    Collection<Task> receiveTasksByCourseAndStatus(Course course, Task.Status status);

    void addTask(Course course, Task task);

    void editTask(Course course, Task task, int id);

    void deleteTask(Course course, int id);
}
