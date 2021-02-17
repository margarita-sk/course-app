package com.leverx.courseapp.user.controller;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.service.StudentService;
import java.util.Collection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

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
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @GetMapping
  public Collection<StudentDtoShort> receiveStudents() {
    var students = studentService.receiveAll();
    return students;
  }

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @GetMapping("/courses")
  public Collection<CourseDtoShort> receiveStudentsAllCourses(@RequestParam String name){
    var courses = studentService.receiveCoursesByStudent(name);
    return courses;
  }

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @GetMapping("/account")
  public StudentDto receiveStudentByName(@RequestParam String name, @AuthenticationPrincipal OidcUser user){
    var student = studentService.receiveStudentByName(name, user);
    return student;
  }

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @PutMapping("/courses/{id}")
  public void assignCourseToStudent(@PathVariable int id, @RequestParam String studentName){
    studentService.assignCourseToStudent(id, studentName);
  }

  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found"),
          @ApiResponse(code = 401, message = "Unauthorized")
  })
  @DeleteMapping("/courses/{id}")
  public void disassignCourseToStudent(@PathVariable int id, @RequestParam String studentName){
    studentService.disassignCourseToStudent(id, studentName);
  }

}