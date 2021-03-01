package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Api(value = "courses")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 404, message = "Not Found"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@RequestMapping(value = "/courses", produces = "application/json")
public class CourseController {

    private final CourseService service;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @GetMapping(value = "/{id}")
    public CourseDto receiveCourseById(@PathVariable int id) {
        var course = service.findCourseById(id);
        return course;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @GetMapping
    public Collection<CourseDtoShort> receiveCourses(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) List<String> tags) {
        var courses = service.findCourses(courseName, date, tags);
        return courses;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Course is created successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public CourseDto addCourse(@RequestBody CourseDto courseDto) {
        return service.addCourse(courseDto);
    }

    @ApiOperation(value = "delete course")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Course was deleted successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        service.removeCourseById(id);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Course was updated successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public CourseDto editCourse(@PathVariable int id, @RequestBody CourseDto courseDto) {
        return service.updateCourseById(id, courseDto);
    }
}
