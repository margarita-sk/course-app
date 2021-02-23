package com.leverx.courseapp.task.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSuchTaskException extends RuntimeException {

  public NoSuchTaskException(int id) {
    super(String.format("Task with Id %d not found", id));
  }
}
