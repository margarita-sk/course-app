package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.service.StudentService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/students/courses")
@ApiResponses(
        value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 401, message = "Unauthorized"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
public class StudentsAssignmentToCoursesController {

    private final StudentService studentService;

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @PutMapping
    public void assignCourseToStudent(
            JwtAuthenticationToken authentication,
            @RequestParam int courseId,
            @RequestParam String email) {
        studentService.assignCourseToStudent(courseId, email);
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @DeleteMapping
    public void disassignCourseToStudent(
            JwtAuthenticationToken authentication,
            @RequestParam int courseId,
            @RequestParam String email) {
        studentService.disassignCourseToStudent(courseId, email);
    }

    private Collection<CourseDtoResponse> transformCoursesIntoResponse(Collection<Course> courses) {
        var coursesDto =
                courses.stream()
                        .map(course -> new CourseDtoResponse(course.getId(), course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return coursesDto;
    }
}
