package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.dto.StudentDtoResponse;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.dto.StudentDtoResponseShort;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.service.StudentService;
import com.leverx.courseapp.validator.Sorting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@RestController
@AllArgsConstructor
@Api(value = "students")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 401, message = "Unauthorized"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@Validated
@RequestMapping(path = "/students", produces = "application/json")
public class StudentController {

    private final StudentService studentService;
    private static final String emailRegexp = "^.+@.+\\..+$";

    @PreAuthorize("hasAuthority('admins')")
    @GetMapping
    public ResponseEntity<Collection<StudentDtoResponseShort>> receiveStudents(
            @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
            @RequestParam(defaultValue = "10") @Min(0) Integer pageSize,
            @RequestParam(defaultValue = "email") @Sorting String sortBy) {
        var students = studentService.receiveAll(pageNo, pageSize, sortBy);
        var studentsDto = transformStudentIntoResponse(students);
        var response = new ResponseEntity<Collection<StudentDtoResponseShort>>(studentsDto, new HttpHeaders(), HttpStatus.OK);
        return response;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(#user.email)")
//    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @GetMapping("/account")
    public StudentDtoResponse findStudentByEmail(@AuthenticationPrincipal OidcUser user, @RequestParam @Pattern(message = "email is invalid", regexp = emailRegexp) String email) {
        var student = studentService.findStudentByEmail(email);
        return transormStudentIntoResponse(student);
    }

    @PostMapping
    public Student addStudent(@RequestBody @Valid StudentDtoParam studentDto) {
        var student = studentService.addStudent(studentDto);
        return student;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @DeleteMapping
    public void deleteStudent(@RequestParam @Pattern(message = "email is invalid", regexp = emailRegexp) String email) {
        studentService.deleteStudent(email);
    }

    private Collection<StudentDtoResponseShort> transformStudentIntoResponse(Collection<Student> students) {
        var studentsDto = students.stream().map(student -> new StudentDtoResponseShort(student.getFirstName(), student.getLastName(), student.getFaculty())).collect(Collectors.toList());
        return studentsDto;
    }

    private StudentDtoResponse transormStudentIntoResponse(Student student) {
        var coursesDto = transformCourseIntoResponse(student.getCourses());
        var studentDto = new StudentDtoResponse(student.getEmail(), student.getFirstName(), student.getLastName(), student.getFaculty(), coursesDto);
        return studentDto;
    }

    private Collection<CourseDtoResponse> transformCourseIntoResponse(Collection<Course> courses) {
        var coursesDto = courses.stream().map(course -> new CourseDtoResponse(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
        return coursesDto;
    }
}
