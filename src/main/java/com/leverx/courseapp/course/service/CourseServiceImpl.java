package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.tag.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private CourseRepository repository;


    @Override
    public Course receiveCourseById(int id) {
        return repository.receiveCourseById(id);
    }

    @Override
    public Collection<Course> receiveAllCourses() {
        return repository.receiveAllCourses();
    }

    @Override
    public Collection<Course> receiveCoursesByDate(LocalDate date) {
        var courses = repository.receiveAllCourses().stream().filter(course -> {
            return (course.getStartAssignmentDate().isAfter(date) && course.getEndAssignmentDate().isBefore(date));
        }).collect(Collectors.toList());
        return courses;
    }

    //TO DO sort collection of courses by decreasing of quantity of tags
    @Override
    public Collection<Course> receiveCoursesByTags(Collection<Tag> searedTags) {
        var courses = repository.receiveAllCourses().stream().filter(course -> {
            return course.getTags().stream().anyMatch(tag -> {
                return searedTags.stream().anyMatch(searchedTag -> searchedTag.equals(tag));
            });
        }).collect(Collectors.toList());
        return courses;
    }

    @Override
    public Collection<Course> receiveCoursesByName(String name) {
        var pattern = Pattern.compile(name);
        var courses = repository.receiveAllCourses().stream().filter(course ->
                pattern.matcher(course.getName()).matches()).collect(Collectors.toList());
        return courses;
    }

    @Override
    public void addCourse(Course course) {
        repository.addCourse(course);
    }

    @Override
    public void deleteCourse(int id) {
        repository.deleteCourse(id);
    }

    @Override
    public void editCourse(Course course) {
        repository.editCourse(course);
    }
}
