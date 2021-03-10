package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDtoParam;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.leverx.courseapp.course.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
public interface CourseService {

    Course findCourseById(@Min(0) int id);

    Course addCourse(@Valid CourseDtoParam courseDto);

    void removeCourseById(@Min(0) int id);

    Course updateCourseById(@Min(0) int id, CourseDtoParam courseDto);

    List<Course> findAllCourses(Pageable paging);

    Collection<Course> findCoursesByDate(LocalDate date, Pageable paging);

    Collection<Course> findCoursesByTags(Collection<String> tags, Pageable paging);

    Collection<Course> findCoursesByName(String name, Pageable paging);

    Collection<Course> findCoursesByStudent(String email);
}
