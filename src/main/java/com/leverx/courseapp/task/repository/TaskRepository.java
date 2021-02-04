package com.leverx.courseapp.task.repository;

import com.leverx.courseapp.task.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {}
