package com.leverx.courseapp.task.service;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.Changeable;
import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.exception.NoSuchTaskException;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.repository.TaskRepository;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Service
@AllArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    @Override
    public Task receiveTaskById(int courseId,  int taskId) {
        var course = findCourseById(courseId);
        var searchedTask =
                course.getTasks().stream()
                        .filter(task -> task.getId() == taskId)
                        .findFirst()
                        .orElseThrow(NoSuchTaskException::new);
        return searchedTask;
    }

    @Override
    public Collection<Task> receiveAllTasksByCourse(int courseId) {
        var course = courseRepository.findById(courseId).orElseThrow(NoSuchCourseException::new);
        var tasks = course.getTasks();
        return tasks;
    }

    @Override
    @Changeable
    public void addTask(int courseId, TaskDto taskDto) {
        var task = new Task(taskDto.getName(), taskDto.getDescription(), courseId);
        taskRepository.save(task);

        var course = findCourseById(courseId);
        var tasks = course.getTasks();
        tasks.add(task);
        course.setTasks(tasks);

        courseRepository.save(course);
    }

    @Override
    @Changeable
    public Task editTask(int courseId, TaskDto taskDto, int taskId) {
        var changedTask = new Task(taskId, taskDto.getName(), taskDto.getDescription(), courseId);
        taskRepository.save(changedTask);

        var course = findCourseById(courseId);
        var tasks = course.getTasks();
        tasks.stream()
                .map(
                        task -> {
                            if (task.getId() == taskId) {
                                task.setName(taskDto.getName());
                                task.setDescription(taskDto.getDescription());
                            }
                            return task;
                        });
        course.setTasks(tasks);
        courseRepository.save(course);
        return changedTask;
    }

    @Override
    @Changeable
    public void deleteTask(int courseId, int taskId) {
        var taskToDelete = taskRepository.findById(taskId);
        taskToDelete.ifPresent(task -> {
            taskRepository.delete(task);

            var course = findCourseById(courseId);
            var tasks = course.getTasks();
            tasks.remove(task);
            course.setTasks(tasks);
            courseRepository.save(course);
        });

    }

    private Course findCourseById(int courseId) {
        var course =
                courseRepository
                        .findById(courseId)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchCourseException(courseId);
                                });
        return course;
    }
}
