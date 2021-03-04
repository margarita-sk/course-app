package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.leverx.courseapp.course.model.Course;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Course findCourseById(int id);

    Course addCourse(CourseDto courseDto);

    void removeCourseById(int id);

    Course updateCourseById(int id, CourseDto courseDto);

    List<Course> findAllCourses(Pageable paging);

    Collection<Course> findCoursesByDate(LocalDate date, Pageable paging);

    Collection<Course> findCoursesByTags(Collection<String> tags, Pageable paging);

    Collection<Course> findCoursesByName(String name, Pageable paging);
}
