package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CourseService {

    CourseDto findCourseById(int id);

    CourseDto addCourse(CourseDto courseDto);

    void removeCourseById(int id);

    CourseDto updateCourseById(int id, CourseDto courseDto);

    Collection<CourseDtoShort> findByParams(String courseName, LocalDate date, String tag, Integer pageNo, Integer pageSize, String sortBy);

    List<CourseDtoShort> findAllCourses(Pageable paging);

    Collection<CourseDtoShort> findCoursesByDate(LocalDate date, Pageable paging);

    Collection<CourseDtoShort> findCoursesByTags(String tagName, Pageable paging);

    Collection<CourseDtoShort> findCoursesByName(String name, Pageable paging);
}
