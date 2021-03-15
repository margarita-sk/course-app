package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import com.leverx.courseapp.user.repository.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    StudentServiceImpl studentService;

    @Mock
    StudentRepository studentRepository;
    @Mock
    CourseRepository courseRepository;
    @Mock
    UserClient client;

    @Mock
    Student student;

    private PageRequest paging;
    private int page;
    private int size;
    private String sorting;

    @BeforeEach
    void setUp() {
        page = 0;
        size = 1;
        sorting = "name";
        paging = PageRequest.of(page, size, Sort.by(sorting));
    }

    @Test
    void returnStudents_whenReceiveAll() {
        var students = List.of(student);
        var studentsPage = new PageImpl<Student>(students);
        when(studentRepository.findAll(paging)).thenReturn(studentsPage);

        var studentsActual = studentService.receiveAll(page, size, sorting);

        assertEquals(students, studentsActual);
    }
}