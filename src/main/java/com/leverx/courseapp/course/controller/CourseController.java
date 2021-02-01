package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.model.Tag;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
public class CourseController {

    @GetMapping("/course/{id}")
    public Course receiveCourseById(@PathVariable int id) {
        return null;
    }

    @GetMapping("/courses")
    public Collection<Course> receiveAllCourses(){
        return  null;
    }

    @GetMapping("/courses/{date}")
    public Collection<Course> receiveCoursesByDate(@PathVariable LocalDate date){
        return null;
    }

    //one tag or collections of tags
    @GetMapping("/courses/{tag}")
    public Collection<Course> receiveCoursesByTags(@PathVariable List<Tag> tags){
        return null;
    }

    @GetMapping("/courses/{name}")
    public Collection<Course> receiveCoursesByName(@PathVariable String name){
        return null;
    }


    @PostMapping("/course")
    public void addCourse(Course course){

    }

    @DeleteMapping("/course/{id}")
    public void deleteCourse(@PathVariable int id){

    }

    @PutMapping("/course/{id}")
    public void editCourse(@PathVariable Course course){

    }
}
