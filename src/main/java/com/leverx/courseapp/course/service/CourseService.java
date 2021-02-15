package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.model.Course;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CourseService {

  CourseDto findCourseById(int id);

  Collection<CourseDtoShort> getAll();

  Collection<CourseDtoShort> findCourses(String name, LocalDate date, Collection<String> tagsNames);

  Collection<CourseDtoShort> findCoursesByDate(LocalDate date);

  Collection<CourseDtoShort> findCoursesByTags(Collection<String> tagsNames);

  Collection<CourseDtoShort> findCoursesByName(String name);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  CourseDto addCourse(CourseDto courseDto);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  void removeCourseById(int id);

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  CourseDto updateCourseById(int id, CourseDto courseDto);
}
