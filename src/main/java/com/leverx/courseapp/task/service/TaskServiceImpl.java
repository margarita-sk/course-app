package com.leverx.courseapp.task.service;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.task.dto.TaskDto;
import com.leverx.courseapp.task.exception.NoSuchTaskException;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.task.repository.TaskRepository;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements TaskService {

  private final CourseRepository courseRepository;
  private final TaskRepository taskRepository;

  @Override
  public Task receiveTaskById(int courseId, int taskId) {
    var course = courseRepository.findById(courseId).orElseThrow(TagNotFoundException::new);
    var searchedTask =
        course.getTasks().stream().filter(task -> task.getId() == taskId).findFirst();
    return searchedTask.orElseThrow(NoSuchTaskException::new);
  }

  @Override
  public Collection<Task> receiveAllTasksByCourse(int courseId) {
    var course = courseRepository.findById(courseId).orElseThrow(TagNotFoundException::new);
    var searchedTasks = course.getTasks();
    return searchedTasks;
  }

  @Override
  public void addTask(int courseId, TaskDto taskDto) {
    var task = new Task(taskDto.getName(), taskDto.getDescription(), courseId);
    taskRepository.save(task);

    var course = courseRepository.findById(courseId).orElseThrow(NoSuchCourseException::new);
    var tasks = course.getTasks();
    tasks.add(task);
    course.setTasks(tasks);

    courseRepository.save(course);
  }

  @Override
  public void editTask(int courseId, TaskDto taskDto, int taskId) {
    var changedTask = new Task(taskId, taskDto.getName(), taskDto.getDescription(), courseId);
    taskRepository.save(changedTask);

    var course = courseRepository.findById(courseId).orElseThrow(NoSuchCourseException::new);
    var tasks = course.getTasks();
    tasks.stream()
        .forEach(
            task -> {
              if (task.getId() == taskId) task = changedTask;
            });
    course.setTasks(tasks);

    courseRepository.save(course);
  }

  @Override
  public void deleteTask(int courseId, int taskId) {
    var task = taskRepository.findById(taskId).orElseThrow(NoSuchTaskException::new);
    taskRepository.delete(task);

    var course = courseRepository.findById(courseId).orElseThrow(NoSuchCourseException::new);
    var tasks = course.getTasks();
    tasks.remove(task);
    course.setTasks(tasks);

    courseRepository.save(course);
  }
}
