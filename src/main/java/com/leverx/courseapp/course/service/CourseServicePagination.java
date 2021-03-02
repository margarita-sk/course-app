package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.CannotDeleteCourseException;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CoursePaginationRepository;
import com.leverx.courseapp.logging.annotations.DbChangeable;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServicePagination implements CourseService {

    private final CoursePaginationRepository courseRepository;
    private final TagRepository tagRepository;

    @Override
    public CourseDto findCourseById(int id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(NoSuchCourseException::new);
        var tags = course.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        var courseDto =
                new CourseDto(
                        course.getName(),
                        course.getDescription(),
                        course.getStartAssignmentDate(),
                        course.getEndAssignmentDate(),
                        tags);
        return courseDto;
    }

    @Override
    @DbChangeable
    public CourseDto addCourse(CourseDto courseDto) {
        var course =
                new Course(
                        courseDto.getName(),
                        courseDto.getDescription(),
                        courseDto.getStartAssignmentDate(),
                        courseDto.getEndAssignmentDate());
        var tags =
                courseDto.getTags().stream()
                        .map(
                                tag ->
                                        tagRepository.findTagsByNameContains(tag).stream()
                                                .findFirst()
                                                .orElseThrow(TagNotFoundException::new))
                        .collect(Collectors.toList());
        course.setTags(tags);
        courseRepository.save(course);
        return courseDto;
    }

    @Override
    @DbChangeable
    public void removeCourseById(int id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(CannotDeleteCourseException::new);
        courseRepository.delete(course);
    }

    @Override
    @DbChangeable
    public CourseDto updateCourseById(int id, CourseDto courseDto) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(NoSuchCourseException::new);
        var changedCourse =
                new Course(
                        course.getId(),
                        courseDto.getName(),
                        courseDto.getDescription(),
                        courseDto.getStartAssignmentDate(),
                        courseDto.getEndAssignmentDate(),
                        course.getTasks(),
                        course.getTags(),
                        course.getStudents());
        courseRepository.save(changedCourse);
        return courseDto;
    }

    @Override
    public Collection<CourseDtoShort> findByParams(String courseName, LocalDate date, String tag, Integer pageNo, Integer pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        if (courseName != null) {
            return findCoursesByName(courseName, paging);
        } else if (date != null) {
            return findCoursesByDate(date, paging);
        } else if (tag != null) {
            return findCoursesByTags(tag, paging);
        } else {
            return findAllCourses(paging);
        }
    }

    @Override
    public List<CourseDtoShort> findAllCourses(Pageable paging) {
        var pagedResult = courseRepository.findAll(paging);
        return pagedResult.stream().map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
    }

    @Override
    public Collection<CourseDtoShort> findCoursesByDate(LocalDate date, Pageable paging) {
        var pagedResult = courseRepository.findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(date, date, paging);
        return pagedResult.stream().map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
    }

    @Override
    public Collection<CourseDtoShort> findCoursesByTags(String tagName, Pageable paging) {
        var tags = tagRepository.findTagsByNameContains(tagName);
        var pagedResult = courseRepository.findCoursesByTagsIn(tags, paging);
        return pagedResult.stream().map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
    }

    @Override
    public Collection<CourseDtoShort> findCoursesByName(String name, Pageable paging) {
        var pagedResult = courseRepository.findCoursesByNameContains(name, paging);
        return pagedResult.stream().map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
    }

}
