package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDtoResponseFull;
import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;
import com.leverx.courseapp.validator.Sorting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RestController
@Api(value = "courses")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 404, message = "Not Found"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@Validated
@RequestMapping(value = "/courses", produces = "application/json")
public class CourseController {

    private final CourseService service;
    private static final String emailRegexp = "^.+@.+\\..+$";

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @GetMapping(value = "/{id}")
    public CourseDtoResponseFull receiveCourseById(@PathVariable @Min(1) int id) {
        var course = service.findCourseById(id);
        return transformCourseIntoDto(course);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @GetMapping
    public ResponseEntity<Collection<CourseDtoResponse>> receiveCourses(
            @RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
            @RequestParam(defaultValue = "5") @Min(0) Integer pageSize,
            @RequestParam(defaultValue = "id") @Sorting String sortBy,
            @RequestParam(required = false)
                    String courseName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate date,
            @RequestParam(required = false)
                    List<String> tags) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Collection<CourseDtoResponse> courses;
        if (courseName != null) {
            courses = findByParams(paging, courseName);
        } else if (date != null) {
            courses = findByParams(paging, date);
        } else if (tags != null) {
            courses = findByParams(paging, tags);
        } else {
            courses = findByParams(paging);
        }
        var response = new ResponseEntity<Collection<CourseDtoResponse>>(courses, new HttpHeaders(), HttpStatus.OK);
        return response;
    }

    @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
    @GetMapping("/students")
    public Collection<CourseDtoResponse> receiveCoursesByStudent(
            JwtAuthenticationToken authentication, @RequestParam @Pattern(message = "email is invalid", regexp = emailRegexp) String email) {
        var courses = service.findCoursesByStudent(email);
        return transformCourseIntoDto(courses);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Course is created successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admins')")
    @PostMapping
    public CourseDtoParam addCourse(@RequestBody @Valid CourseDtoParam courseDto) {
        var course = service.addCourse(courseDto);
        return transformCourseIntoParam(course);
    }

    @ApiOperation(value = "delete course")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Course was deleted successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAuthority('admins')")
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable @Min(0) int id) {
        service.removeCourseById(id);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Course was updated successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public CourseDtoParam editCourse(@PathVariable @Min(0) int id, @RequestBody @Valid CourseDtoParam courseDto) {
        var course = service.updateCourseById(id, courseDto);
        return transformCourseIntoParam(course);
    }


    private Collection<CourseDtoResponse> findByParams(Pageable paging, String courseName) {
        var courses = service.findCoursesByName(courseName, paging);
        return transformCourseIntoDto(courses);
    }

    private Collection<CourseDtoResponse> findByParams(Pageable paging, LocalDate date) {
        var courses = service.findCoursesByDate(date, paging);
        return transformCourseIntoDto(courses);
    }

    private Collection<CourseDtoResponse> findByParams(Pageable paging, Collection<String> tags) {
        var courses = service.findCoursesByTags(tags, paging);
        return transformCourseIntoDto(courses);
    }

    private Collection<CourseDtoResponse> findByParams(Pageable paging) {
        var courses = service.findAllCourses(paging);
        return transformCourseIntoDto(courses);
    }

    private Collection<CourseDtoResponse> transformCourseIntoDto(Collection<Course> courses) {
        var coursesDto = courses.stream().map(course -> new CourseDtoResponse(course.getId(), course.getName(), course.getDescription())).collect(Collectors.toList());
        return coursesDto;
    }

    private CourseDtoParam transformCourseIntoParam(Course course) {
        var tags = course.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        var coursesDto = new CourseDtoParam(course.getName(), course.getDescription(), course.getStartAssignmentDate(), course.getEndAssignmentDate(), tags);
        return coursesDto;
    }

    private CourseDtoResponseFull transformCourseIntoDto(Course course) {
        var tags = course.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        var coursesDto = new CourseDtoResponseFull(course.getName(), course.getDescription(), course.getStartAssignmentDate(), course.getEndAssignmentDate(), tags);
        return coursesDto;
    }
}
