package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.model.Student;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
public interface StudentService {

    Collection<Student> receiveAll(Integer pageNo, Integer pageSize, String sortBy);

    Student findStudentByEmail(String email);

    void deleteStudent(String email);

    Student addStudent(@Valid StudentDtoParam studentDto);

    // assignment to courses

    void assignCourseToStudent(@Min(0) int courseId, String email);

    void disassignCourseToStudent(@Min(0) int courseId, String email);
}
