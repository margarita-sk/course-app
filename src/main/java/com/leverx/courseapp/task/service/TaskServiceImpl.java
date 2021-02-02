package com.leverx.courseapp.task.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.task.exception.NoSuchTaskException;
import com.leverx.courseapp.task.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private CourseRepository repository;

    @Override
    public Task receiveTaskById(Course course, int id) {
        var searchedTask = course.getTasks().stream().filter(task -> task.getId() == id).findFirst();
        return searchedTask.orElseThrow(NoSuchTaskException::new);
    }

    @Override
    public Collection<Task> receiveAllTasksByCourse(Course course) {
        var tasks = course.getTasks();
        return tasks;
    }

    @Override
    public Collection<Task> receiveTasksByCourseAndStatus(Course course, Task.Status status) {
        var tasks = course.getTasks().stream().filter(task -> {
            return task.getStatus().equals(status);
        }).collect(Collectors.toList());
        return tasks;
    }

    //I'm not sure I should do it
    @Override
    public void addTask(Course course, Task task) {
        var tasks = course.getTasks();
        tasks.add(task);
        course.setTasks(tasks);
        repository.editCourse(course);
    }

    @Override
    public void editTask(Course course, Task task, int id) {
        var tasks = course.getTasks();
        var taskWhichIsToChange = tasks.stream().filter(task1 -> task1.getId() == id).findFirst().orElseThrow(NoSuchTaskException::new);
        tasks.remove(taskWhichIsToChange);
        tasks.add(task);
        course.setTasks(tasks);
        repository.editCourse(course);
    }

    @Override
    public void deleteTask(Course course, int id) {
        var tasks = course.getTasks();
        var taskWhichIsToChange = tasks.stream().filter(task1 -> task1.getId() == id).findFirst().orElseThrow(NoSuchTaskException::new);
        tasks.remove(taskWhichIsToChange);
        course.setTasks(tasks);
        repository.editCourse(course);
    }
}
