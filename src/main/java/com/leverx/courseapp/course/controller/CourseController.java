package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.leverx.courseapp.user.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CourseController {

    private CourseService service;
    private StudentService studentService;

    @GetMapping("/")
    public Collection<String> startPage() {
        var uri = new ArrayList<String>();
        uri.add("/courses/{id}");
        uri.add("/courses");
        uri.add("/courses/find-by-date");
        uri.add("/courses/find-by-tags");
        uri.add("/courses/find-by-name");
        return uri;
    }

    @GetMapping("/courses/{id}")
    public Course receiveCourseById(@PathVariable int id) {
        var course = service.findCourseById(id);
        return course;
    }

    @GetMapping("/courses")
    public Collection<Course> receiveAllCourses() {
        var courses = service.getAll();
        return courses;
    }

    // TODO: I'm not sure i can use this uri
    @GetMapping("/courses/find-by-date")
    public Collection<Course> receiveCoursesByDate(@RequestParam String date) {
        var course = service.findCoursesByDate(LocalDate.parse(date));
        return course;
    }

    // TODO make something with exceptions
    @GetMapping("/courses/find-by-tags")
    public Collection<Course> receiveCoursesByTags(@RequestParam List<String> tags) {
        var courses = service.findCoursesByTags(tags);
        return courses;
    }

    @GetMapping("/courses/find-by-name")
    public Collection<Course> receiveCoursesByName(@RequestParam String name) {
        var courses = service.findCoursesByName(name);
        return courses;
    }

    @GetMapping("/courses/find-by-student")
    public Collection<Course> receiveCoursesByStudent(@RequestParam String name) {
        var student = studentService.findStudentByName(name);
        var courses = service.findCoursesByStudentName(name);
        return courses;
    }

    @PostMapping("/courses")
    public void addCourse(@ModelAttribute CourseDto courseDto) {
        service.addCourse(courseDto);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable int id) {
        service.removeCourseById(id);
    }

    @PutMapping("/courses/{id}")
    public Course editCourse(@PathVariable int id, @ModelAttribute CourseDto courseDto) {
        var course = service.updateCourseById(id, courseDto);
        return course;
    }
}
