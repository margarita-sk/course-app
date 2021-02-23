package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import com.leverx.courseapp.user.dto.StudentOktaDto;
import com.leverx.courseapp.user.service.StudentService;

import com.okta.sdk.resource.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Api(value = "students")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping(path = "/students", produces = "application/json")
public class StudentController {

    private final StudentService studentService;

    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    public Collection<StudentOktaDto> receiveStudents() {
        return studentService.receiveAll();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/account")
    public StudentOktaDto findStudentByEmail(JwtAuthenticationToken authentication, @RequestParam String email) {
        return studentService.findStudentByEmail(email);
    }


    //TODO this method works only if user is authenticated
    //TODO also this method can throw com.okta.sdk.resource.ResourceException: HTTP 400
    @PostMapping
    public User addStudent(@RequestBody StudentDtoRegistration studentDto) {
        var user = studentService.addStudent(studentDto);
        return user;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping
    public void deleteStudent(@RequestParam String email) {
        studentService.deleteStudent(email);
    }


}