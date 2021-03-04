package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.exception.CannotDeleteCourseException;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.Changeable;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;

    @Override
    public Course findCourseById(int id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(NoSuchCourseException::new);
        return course;
    }

    @Override
    @Changeable
    public Course addCourse(CourseDto courseDto) {
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
        return course;
    }

    @Override
    @Changeable
    public void removeCourseById(int id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(CannotDeleteCourseException::new);
        courseRepository.delete(course);
    }

    @Override
    @Changeable
    public Course updateCourseById(int id, CourseDto courseDto) {
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
        return changedCourse;
    }


    @Override
    public List<Course> findAllCourses(Pageable paging) {
        var pagedResult = courseRepository.findAll(paging);
        return pagedResult.getContent();
    }

    @Override
    public Collection<Course> findCoursesByDate(LocalDate date, Pageable paging) {
        var pagedResult = courseRepository.findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(date, date, paging);
        return pagedResult.getContent();
    }

    @Override
    public Collection<Course> findCoursesByTags(Collection<String> tagNames, Pageable paging) {
       var tags = tagNames.stream().map(tag -> tagRepository.findTagsByNameContains(tag)).flatMap(Collection::stream).collect(Collectors.toList());
        var pagedResult = courseRepository.findCoursesByTagsIn(tags, paging);
        return pagedResult.getContent();
    }

    @Override
    public Collection<Course> findCoursesByName(String name, Pageable paging) {
        var pagedResult = courseRepository.findCoursesByNameContains(name, paging);
        return pagedResult.getContent();
    }

}
