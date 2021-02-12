package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.repository.TagRepository;
import com.leverx.courseapp.user.repository.StudentRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final TagRepository tagRepository;
    private final StudentRepository studentRepository;

    @Override
    public Collection<CourseDtoParam> getAll() {
        var courseDtoParams =
                StreamSupport.stream(repository.findAll().spliterator(), false)
                        .map(course -> new CourseDtoParam(course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return courseDtoParams;
    }

    @Override
    public Course findCourseById(int id) {
        var course = repository.findById(id).orElseThrow(NoSuchCourseException::new);
        //    var courseDto = new CourseDtoParam(course.getName(), course.getDescription());
        return course;
    }



    @Override
    public Collection<CourseDtoParam> findCoursesByDate(LocalDate date) {
        var courses =
                repository.findByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(
                        date, date);
        var courseDtoParams = transformCoursesToCoursesDtoParams(courses);
        return courseDtoParams;
    }

    @Override
    public Collection<CourseDtoParam> findCoursesByTags(Collection<String> tagsNames) {
        var tags =
                tagsNames.stream()
                        .map(
                                name ->
                                        tagRepository.findTagsByNameContains(name).stream()
                                                .findFirst()
                                                .orElseThrow(TagNotFoundException::new))
                        .collect(Collectors.toList());
        var courses = repository.findCoursesByTagsIn(tags);
        var courseDtoParams = transformCoursesToCoursesDtoParams(courses);
        return courseDtoParams;
    }

    @Override
    public Collection<CourseDtoParam> findCoursesByName(String name) {
        var courses = repository.findCoursesByNameContains(name);
        var courseDtoParams = transformCoursesToCoursesDtoParams(courses);
        return courseDtoParams;
    }

    @Override
    public Collection<CourseDtoParam> findCourses(String name, LocalDate date, Collection<String> tagsNames) {
        if(name != null)
            return findCoursesByName(name);
        if(date != null)
            return findCoursesByDate(date);
        if(tagsNames != null)
            return findCoursesByTags(tagsNames);
        return getAll();
    }

    @Override
    public void addCourse(CourseDto courseDto) {
        var course =
                new Course(
                        courseDto.getName(),
                        courseDto.getDescription(),
                        courseDto.getStartAssignmentDate(),
                        courseDto.getEndAssignmentDate());
        repository.save(course);
    }

    @Override
    public void removeCourseById(int id) {
        var course = repository.findById(id).orElseThrow(NoSuchCourseException::new);
        repository.delete(course);
    }

    @Override
    public Course updateCourseById(int id, CourseDto courseDto) {
        var course = repository.findById(id).orElseThrow(NoSuchCourseException::new);
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
        return changedCourse;
    }

    public Collection<CourseDtoParam> transformCoursesToCoursesDtoParams(Collection<Course> courses) {
        var courseDtoParams =
                courses.stream()
                        .map(course -> new CourseDtoParam(course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return courseDtoParams;
    }
}
