package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.CannotDeleteCourseException;
import com.leverx.courseapp.course.exception.CourseAlreadyExsistsException;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.DbChangeable;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.repository.TagRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final TagRepository tagRepository;

    @Override
    public Collection<CourseDtoShort> getAll() {
        var courseDtoParams =
                StreamSupport.stream(repository.findAll().spliterator(), false)
                        .map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return courseDtoParams;
    }

    @Override
    public CourseDto findCourseById(int id) {
        var course =
                repository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchCourseException(id);
                                });
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
    public Collection<CourseDtoShort> findCoursesByDate(LocalDate date) {
        var courses =
                repository.findByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(
                        date, date);
        var courseDtoParams = transformCoursesToCoursesDtoShort(courses);
        return courseDtoParams;
    }

    @Override
    public Collection<CourseDtoShort> findCoursesByTags(Collection<String> tagsNames) {
        var tags =
                tagsNames.stream()
                        .map(
                                name ->
                                        tagRepository.findTagsByNameContains(name).stream()
                                                .findFirst()
                                                .orElseThrow(TagNotFoundException::new))
                        .collect(Collectors.toList());
        var courses = repository.findCoursesByTagsIn(tags);
        var courseDtoParams = transformCoursesToCoursesDtoShort(courses);
        return courseDtoParams;
    }

    @Override
    public Collection<CourseDtoShort> findCoursesByName(String name) {
        var courses = repository.findCoursesByNameContains(name);
        var courseDtoParams = transformCoursesToCoursesDtoShort(courses);
        return courseDtoParams;
    }

    //TODO fix it
    @Override
    public Collection<CourseDtoShort> findCourses(
            String name, LocalDate date, Collection<String> tagsNames) {
        if (name != null) {return findCoursesByName(name);}
        if (date != null) {return findCoursesByDate(date);}
        if (tagsNames != null) {return findCoursesByTags(tagsNames);}
        return getAll();
    }

    @DbChangeable
    @Override
    public CourseDto addCourse(CourseDto courseDto) {
        var existingCourse = findCoursesByName(courseDto.getName()).stream().filter(course -> course.getName().equals(courseDto.getName())).findFirst();
        existingCourse.ifPresent(courseDtoShort -> {
            throw new CourseAlreadyExsistsException(courseDto.getName());
        });
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
        repository.save(course);
        return courseDto;
    }

    @Override
    @DbChangeable
    public void removeCourseById(int id) {
        var course =
                repository
                        .findById(id)
                        .orElseThrow(CannotDeleteCourseException::new);
        repository.delete(course);
    }

    @Override
    @DbChangeable
    public CourseDto updateCourseById(int id, CourseDto courseDto) {
        var course =
                repository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchCourseException(id);
                                });
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
        repository.save(changedCourse);
        return courseDto;
    }

    public Collection<CourseDtoShort> transformCoursesToCoursesDtoShort(Collection<Course> courses) {
        var courseDtoParams =
                courses.stream()
                        .map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return courseDtoParams;
    }
}
