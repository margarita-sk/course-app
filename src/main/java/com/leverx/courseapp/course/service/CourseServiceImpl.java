package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;
import com.leverx.courseapp.task.exception.NoSuchTaskException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.leverx.courseapp.user.exception.NoSuchUserException;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final TagRepository tagRepository;
    private final StudentRepository studentRepository;

    @Override
    public Collection<Course> getAll() {
        var courses =
                StreamSupport.stream(repository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
        return courses;
    }

    @Override
    public Course findCourseById(int id) {
        var course = repository.findById(id);
        return course.orElseThrow(NoSuchCourseException::new);
    }

    @Override
    public Collection<Course> findCoursesByDate(LocalDate date) {
        var courses =
                repository.findByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(
                        date, date);
        return courses;
    }

    @Override
    public Collection<Course> findCoursesByTags(Collection<String> tagsNames) {
        var tags = new LinkedList<Tag>();
        tagsNames.forEach(
                name -> {
                    var tag =
                            tagRepository.findTagsByNameContains(name).stream()
                                    .findFirst()
                                    .orElseThrow(NoSuchTaskException::new);
                    tags.add(tag);
                });
        var courses = repository.findCoursesByTagsIn(tags);
        return courses;
    }

    @Override
    public Collection<Course> findCoursesByName(String name) {
        var courses = repository.findCoursesByNameContains(name);
        return courses;
    }

    @Override
    public Collection<Course> findCoursesByStudentName(String name) {
        var student = studentRepository.findByNameContains(name);
        var courses = repository.findCoursesByStudentsIn(List.of(student));
        return courses;
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
}
