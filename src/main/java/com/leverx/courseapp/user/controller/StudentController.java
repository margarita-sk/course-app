package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.dto.StudentOktaDto;
import com.leverx.courseapp.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;

import lombok.AllArgsConstructor;
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
    public Collection<StudentOktaDto> receiveStudents() {
        return studentService.receiveAll();
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @GetMapping("/account")
    public StudentOktaDto findStudentByEmail(
            JwtAuthenticationToken authentication, @RequestParam String email) {
        return studentService.findStudentByEmail(email);
    }

    @PreAuthorize("hasAuthority('admins') or #studentDto.email.equals(authentication.name)")
    @PutMapping
    public StudentOktaDto registrateStudent(@RequestBody StudentDtoShort studentDto) {
        var user = studentService.registerStudentInDb(studentDto);
        return user;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @DeleteMapping
    public void deleteStudent(@RequestParam String email) {
        studentService.deleteStudent(email);
    }
}
