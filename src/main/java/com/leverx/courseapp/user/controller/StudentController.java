package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Api(value = "students")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 401, message = "Unauthorized"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@RequestMapping(path = "/students", produces = "application/json")
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAuthority('admins')")
    @GetMapping
    public ResponseEntity<Collection<StudentDto>> receiveStudents(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "email") String sortBy) {
        var students = studentService.receiveAll(pageNo, pageSize, sortBy);
        var response = new ResponseEntity<Collection<StudentDto>>(students, new HttpHeaders(), HttpStatus.OK);
        return response;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @GetMapping("/account")
    public StudentDto findStudentByEmail(
            JwtAuthenticationToken authentication, @RequestParam String email) {
        return studentService.findStudentByEmail(email);
    }

    @PreAuthorize("hasAuthority('admins') or #studentDto.email.equals(authentication.name)")
    @PutMapping
    public StudentDto registrateStudent(@RequestBody StudentDto studentDto) {
        var user = studentService.registerStudentInDb(studentDto);
        return user;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @DeleteMapping
    public void deleteStudent(@RequestParam String email) {
        studentService.deleteStudent(email);
    }
}
