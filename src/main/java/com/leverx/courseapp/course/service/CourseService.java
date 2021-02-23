package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;
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

  @PreAuthorize("hasAuthority('admins')")
  CourseDto addCourse(CourseDto courseDto);

  @PreAuthorize("hasAuthority('admins')")
  void removeCourseById(int id);

  @PreAuthorize("hasAuthority('admins')")
  CourseDto updateCourseById(int id, CourseDto courseDto);
}
