package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;
import com.leverx.courseapp.tag.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
public class CourseController {

    private CourseService service;

    @GetMapping("/")
    public Collection<String> startPage() {
        var uri = new ArrayList<String>();
        uri.add("/course/{id}");
        return uri;
    }

    //TODO: resolve the bug
    // here we have a problem of parsing Course into JSON. Unfortunately,
    // it turns into endless cycle of getting tags from courses and courses from tags
    // and the same thing with tasks.
    @GetMapping("/course/{id}")
    public String receiveCourseById(@PathVariable int id) {
        var course = service.receiveCourseById(id);
        return course.toString();
    }

    @GetMapping("/courses")
    public Collection<Course> receiveAllCourses() {
        var courses = service.receiveAllCourses();
        return courses;
    }

    @GetMapping("/courses/{date}")
    public Collection<Course> receiveCoursesByDate(@PathVariable LocalDate date) {
        var courses = service.receiveCoursesByDate(date);
        return courses;
    }

    @GetMapping("/courses/{tags}")
    public Collection<Course> receiveCoursesByTags(@PathVariable List<Tag> tags) {
        var courses = service.receiveCoursesByTags(tags);
        return courses;
    }

    @GetMapping("/courses/{name}")
    public Collection<Course> receiveCoursesByName(@PathVariable String name) {
        var courses = service.receiveCoursesByName(name);
        return courses;
    }

    @PostMapping("/course")
    public void addCourse(Course course) {
        service.addCourse(course);
    }

    @DeleteMapping("/course/{id}")
    public void deleteCourse(@PathVariable int id) {
        service.deleteCourse(id);
    }

    @PutMapping("/course/{id}")
    public void editCourse(@PathVariable Course course) {
        service.editCourse(course);
    }
}
