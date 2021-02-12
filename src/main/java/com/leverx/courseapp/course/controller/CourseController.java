package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;
import com.leverx.courseapp.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Api(value = "courses")
@RequestMapping(value = "/courses", produces = "application/json")
public class CourseController {

    private final CourseService service;
    private final StudentService studentService;

    @ApiOperation(value = "view course by id", response = Course.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully received course"),
                    @ApiResponse(code = 400, message = "Bad request"),
                    @ApiResponse(code = 401, message = "You are not authorized to view this resource"),
                    @ApiResponse(code = 404, message = "The resource is not found")
            })
    @GetMapping(value = "/{id}")
    public Course receiveCourseById(@PathVariable int id) {
        var course = service.findCourseById(id);
        return course;
    }

    @ApiOperation(value = "view all courses")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully received courses"),
                    @ApiResponse(code = 401, message = "You are not authorized to view this resource"),
                    @ApiResponse(code = 404, message = "The resource is not found"),
            })
    @GetMapping
    public Collection<CourseDtoParam> receiveCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) List<String> tags) {
        var courses = service.findCourses(courseName, date, tags);
        return courses;
    }

    @ApiOperation(value = "add new course")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 401, message = "You are not authorized to perform this request")
            })
    @PostMapping
    public void addCourse(@RequestBody CourseDto courseDto) {
        service.addCourse(courseDto);
    }

    @ApiOperation(value = "delete course")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 401, message = "You are not authorized to perform this request")
            })
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        service.removeCourseById(id);
    }

    @ApiOperation(value = "edit course")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success"),
                    @ApiResponse(code = 401, message = "You are not authorized to perform this request")
            })
    @PutMapping("/{id}")
    public Course editCourse(@PathVariable int id, @ModelAttribute CourseDto courseDto) {
        var course = service.updateCourseById(id, courseDto);
        return course;
    }
}
