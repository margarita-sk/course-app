package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.Changeable;
import com.leverx.courseapp.tag.repository.TagRepository;
import com.leverx.courseapp.user.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
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
    public Course addCourse(CourseDtoParam courseDto) {
        var course =
                new Course(
                        courseDto.getName(),
                        courseDto.getDescription(),
                        courseDto.getStartAssignmentDate(),
                        courseDto.getEndAssignmentDate());
        var tags = courseDto.getTags();
        var searchedTags = StreamSupport
                .stream(tagRepository.findAll().spliterator(), false)
                .filter(tag -> tags.contains(tag.getName())).collect(Collectors.toList());
        course.setTags(searchedTags);
        courseRepository.save(course);
        return course;
    }

    @Override
    @Changeable
    public void removeCourseById(int id) {
        var courseToDelelte =
                courseRepository
                        .findById(id);
        courseToDelelte.ifPresent(course -> courseRepository.delete(course));
    }

    @Override
    @Changeable
    public Course updateCourseById(int id, CourseDtoParam courseDto) {
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
        var courses = pagedResult.getContent();
        return courses;
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

    @Override
    public Collection<Course> findCoursesByStudent(String email) {
        var student = studentRepository.findStudentByEmail(email);
        var courses = student.getCourses();
        return courses;
    }

}
