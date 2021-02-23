package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.service.StudentService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/students/courses")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
public class StudentsAssignmentToCoursesController {

    private final StudentService studentService;

    @GetMapping
    public Collection<CourseDtoShort> receiveCoursesByStudent(JwtAuthenticationToken authentication, @RequestParam String email) {
        var courses = studentService.receiveCoursesByStudent(email);
        return courses;
    }

    @PutMapping
    public void assignCourseToStudent(JwtAuthenticationToken authentication, @RequestParam int courseId, @RequestParam String email) {
        studentService.assignCourseToStudent(courseId, email);
    }

    @DeleteMapping
    public void disassignCourseToStudent(JwtAuthenticationToken authentication, @RequestParam int courseId, @RequestParam String email) {
        studentService.disassignCourseToStudent(courseId, email);
    }
}
