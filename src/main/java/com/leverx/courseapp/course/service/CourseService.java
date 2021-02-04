package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.model.Course;
import java.time.LocalDate;
import java.util.Collection;

public interface CourseService {

  Collection<Course> getAll();

  Course findCourseById(int id);

  Collection<Course> findCoursesByDate(LocalDate date);

  Collection<Course> findCoursesByTags(Collection<String> tagsNames);

  Collection<Course> findCoursesByName(String name);

  Collection<Course> findCoursesByStudentName(String name);

  void addCourse(CourseDto courseDto);

  void removeCourseById(int id);

  Course updateCourseById(int id, CourseDto courseDto);
}
