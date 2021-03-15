package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.dto.CourseDtoParam;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @InjectMocks
    CourseServiceImpl courseService;

    @Mock
    CourseRepository courseRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    TagRepository tagRepository;

    private CourseDtoParam courseDtoParam;
    private Course course;
    private Collection<Tag> tags;
    private Pageable paging;
    private Student student;

    @BeforeEach
    void setUp() {
        var name = "test course";
        var description = "description";
        var startAssignmentDate = LocalDate.parse("2021-03-12");
        var endAssignmentDate = LocalDate.parse("2021-04-12");

        student = new Student("test@test.com", "Test Student", "Student", "test");
        var students = List.of(student);
        tags = List.of(new Tag(3, "test"));
        courseDtoParam = new CourseDtoParam(name, description, startAssignmentDate, endAssignmentDate, List.of("test"));
        course = new Course(0, name, description, startAssignmentDate, endAssignmentDate, null, tags, students);
        student.setCourses(List.of(course));
        paging = PageRequest.of(0, 1, Sort.by("name"));
    }

    @Test
    void shouldReturnCourse_whenFindCourseById_givenId() {
        final int id = course.getId();
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        var actualCourse = courseService.findCourseById(id);

        assertEquals(course, actualCourse);
    }

    @Test
    void shouldThrowNoSuchCourseException_whenFindCourseById_givenId() {
        final int id = 3;
        when(courseRepository.findById(id)).thenThrow(NoSuchCourseException.class);

        assertThrows(NoSuchCourseException.class, () -> courseService.findCourseById(id));
    }

    @Test
    void shouldReturnCourses_whenFindAllCourses_givenPaging() {
        var courses = List.of(course);
        var coursePage = new PageImpl<Course>(courses);
        when(courseRepository.findAll(paging)).thenReturn(coursePage);

        assertTrue(courseService.findAllCourses(paging).size() == 1);
    }

    @Test
    void shouldReturnCourse_whenAddCourse_givenCourseDtoParam() {
        when(tagRepository.findAll()).thenReturn((Iterable<Tag>) tags);
        when(courseRepository.save(course)).thenReturn(course);

        var courseActualSaved = courseService.addCourse(courseDtoParam);

        assertEquals(course, courseActualSaved);
    }


    @Test
    void shouldReturnTrue_whenRemoveCourseById_givenCourseId() {
        var courses = new ArrayList<>();
        courses.add(course);
        final var id = course.getId();
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        doAnswer(invocation -> courses.remove(course)).when(courseRepository).delete(course);

        courseService.removeCourseById(id);

        assertTrue(courses.isEmpty());
    }

    @Test
    void shouldReturnCourse_whenUpdateCourseById_givenIdAndCourseDtoParam() {
        final var id = course.getId();
        courseDtoParam.setName("changed name");
        var changedCourse = new Course(id, courseDtoParam.getName(), course.getDescription(), course.getStartAssignmentDate(), course.getEndAssignmentDate(), course.getTasks(), course.getTags(), course.getStudents());
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(courseRepository.save(changedCourse)).thenReturn(changedCourse);

        var courseActual = courseService.updateCourseById(id, courseDtoParam);

        assertEquals(changedCourse, courseActual);
    }

    @Test
    void shouldReturnCourses_whenFindCoursesByDate_givenDate() {
        var date = LocalDate.parse("2021-03-12");
        var courses = List.of(course);
        var coursePage = new PageImpl<Course>((List<Course>) courses);
        when(courseRepository.findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(date, date, paging)).thenReturn(coursePage);

        var actualCourse = courseService.findCoursesByDate(date, paging);

        assertEquals(courses, actualCourse);
    }

    @Test
    void shouldReturnCourses_whenFindCoursesByTags_givenTags() {
        var tagsNames = tags.stream().map(tag -> tag.getName()).collect(Collectors.toList());
        var courses = List.of(course);
        var coursePage = new PageImpl<Course>((List<Course>) courses);
        when(tagRepository.findTagsByNameContains(tagsNames.get(0))).thenReturn(tags);
        when(courseRepository.findCoursesByTagsIn(tags, paging)).thenReturn(coursePage);

        var coursesActual = courseService.findCoursesByTags(tagsNames, paging);

        assertEquals(courses, coursesActual);
    }

    @Test
    void shouldReturnCourses_whenFindCoursesByName_givenName() {
        var name = "test course";
        var courses = List.of(course);
        var coursePage = new PageImpl<Course>((List<Course>) courses);
        when(courseRepository.findCoursesByNameContains(name, paging)).thenReturn(coursePage);

        var coursesActual = courseService.findCoursesByName(name, paging);

        assertEquals(courses, coursesActual);
    }

    @Test
    void shouldThrowNoSuchCourseException_whenFindCoursesByName_givenName() {
        var name = "no such course";
        var courses = List.of(course);
        var coursePage = new PageImpl<Course>((List<Course>) courses);
        when(courseRepository.findCoursesByNameContains(name, paging)).thenThrow(NoSuchCourseException.class);

        assertThrows(NoSuchCourseException.class, () -> courseService.findCoursesByName(name, paging));
    }

    @Test
    void shouldReturnCourses_whenFindCoursesByStudent_givenStudent() {
        var email = "test@test.com";
        var courses = List.of(course);
        when(studentRepository.findStudentByEmail(email)).thenReturn(student);

        var coursesActual = courseService.findCoursesByStudent(email);

        assertEquals(courses, coursesActual);
    }
}