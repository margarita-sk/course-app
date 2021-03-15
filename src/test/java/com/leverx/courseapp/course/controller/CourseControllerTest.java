package com.leverx.courseapp.course.controller;

import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.dto.CourseDtoResponse;
import com.leverx.courseapp.course.dto.CourseDtoResponseFull;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.service.CourseService;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.validator.Sorting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @InjectMocks
    CourseController controller;

    @Mock
    CourseService service;

    private Course course;
    private Collection<Tag> tags;
    private Pageable paging;
    private Student student;
    private CourseDtoResponseFull courseDtoResponseFull;
    private CourseDtoResponse courseDtoResponse;
    private CourseDtoParam courseDtoParam;

    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String courseName;
    private LocalDate date;
    private List<String> tagsNames;

    @BeforeEach
    void setUp() {
        var name = "test course";
        var description = "description";
        var startAssignmentDate = LocalDate.parse("2021-03-12");
        var endAssignmentDate = LocalDate.parse("2021-04-12");

        tags = List.of(new Tag(3, "test"));
        course = new Course(0, name, description, startAssignmentDate, endAssignmentDate, null, tags, null);
        courseDtoResponseFull = new CourseDtoResponseFull(name, description, startAssignmentDate, endAssignmentDate, List.of("test"));
        courseDtoResponse = new CourseDtoResponse(0, name, description);
        courseDtoParam = new CourseDtoParam(name, description, startAssignmentDate, endAssignmentDate, List.of("test"));

        pageNo = 0;
        pageSize = 1;
        sortBy = "name";
        paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    }

    @Test
    void shouldReturnCourse_whenReceiveCourseById_givenId() {
        final int id = 0;
        when(service.findCourseById(id)).thenReturn(course);

        var courseDtoActual = controller.receiveCourseById(id);

        assertEquals(courseDtoResponseFull, courseDtoActual);
    }

    @Test
    void shouldReturnCourses_whenReceiveCourses() {
        var courses = List.of(course);
        var coursesDto = List.of(courseDtoResponse);
        var responseExpected = new ResponseEntity<Collection<CourseDtoResponse>>(coursesDto, new HttpHeaders(), HttpStatus.OK);
        when(service.findAllCourses(paging)).thenReturn(courses);

        var responseActual = controller.receiveCourses(pageNo, pageSize, sortBy, courseName, date, tagsNames);

        assertEquals(responseExpected, responseActual);
    }

    @Test
    void shouldReturnCourses_whenReceiveCoursesByStudent_givenEmail() {
        var courses = List.of(course);
        var coursesDto = List.of(courseDtoResponse);
        var email = "test@test.com";
        when(service.findCoursesByStudent(email)).thenReturn(courses);

        var coursesActualDto = controller.receiveCoursesByStudent(null, email);

        assertEquals(coursesDto, coursesActualDto);
    }

    @Test
    void shouldReturnCourseDtoParam_whenAddCourse_givenCourseDtoParam() {
        when(service.addCourse(courseDtoParam)).thenReturn(course);

        var courseDtoActual = controller.addCourse(courseDtoParam);

        assertEquals(courseDtoParam, courseDtoActual);
    }

    @Test
    void shouldReturnTrue_whenDeleteCourse_givenCourseId() {
        var courses = new ArrayList<>();
        courses.add(course);
        final var id = course.getId();
        doAnswer(invocation -> courses.remove(course)).when(service).removeCourseById(id);

        controller.deleteCourse(id);

        assertTrue(courses.isEmpty());
    }

    @Test
    void shouldReturnCourseDtoParam_whenEditCourse_givenCourseDtoParam() {
        var changedCourseName = "changed name";
        int id = course.getId();
        var courseDtoParamChanged = new CourseDtoParam(changedCourseName, courseDtoParam.getDescription(), courseDtoParam.getStartAssignmentDate(), courseDtoParam.getEndAssignmentDate(), courseDtoParam.getTags());
        var courseChanged = new Course(id, changedCourseName, course.getDescription(), course.getStartAssignmentDate(), course.getEndAssignmentDate(), null, course.getTags(), null);
        when(service.updateCourseById(id, courseDtoParamChanged)).thenReturn(courseChanged);

        var courseDtoActual = controller.editCourse(id, courseDtoParamChanged);

        assertEquals(courseDtoParamChanged, courseDtoActual);
    }
}