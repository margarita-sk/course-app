package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.model.Course;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CourseService {

  Collection<CourseDtoParam> getAll();

  Course findCourseById(int id);

  Collection<CourseDtoParam> findCourses(String name, LocalDate date, Collection<String> tagsNames);

  Collection<CourseDtoParam> findCoursesByDate(LocalDate date);

  Collection<CourseDtoParam> findCoursesByTags(Collection<String> tagsNames);

  Collection<CourseDtoParam> findCoursesByName(String name);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void addCourse(CourseDto courseDto);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void removeCourseById(int id);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  Course updateCourseById(int id, CourseDto courseDto);
}
