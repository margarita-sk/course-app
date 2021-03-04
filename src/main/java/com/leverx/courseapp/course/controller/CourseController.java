package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDto;
import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;
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
        return transformCourseIntoDto(course);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    @GetMapping
    public ResponseEntity<Collection<CourseDtoResponse>> receiveCourses(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
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

    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Course is created successfully"),
                    @ApiResponse(code = 401, message = "Unauthorized")
            })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admins')")
    @PostMapping
    public CourseDto addCourse(@RequestBody CourseDto courseDto) {
        var course = service.addCourse(courseDto);
        return transformCourseIntoDto(course);
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
        var course = service.updateCourseById(id, courseDto);
        return transformCourseIntoDto(course);
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

    private CourseDto transformCourseIntoDto(Course course) {
        var tags = course.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());
        var coursesDto = new CourseDto(course.getName(), course.getDescription(), course.getStartAssignmentDate(), course.getEndAssignmentDate(), tags);
        return coursesDto;
    }
}
