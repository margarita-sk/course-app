package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> receiveAll(Integer pageNo, Integer pageSize, String sortBy);

    Student findStudentByEmail(String email);

    void deleteStudent(String email);

    Student addStudent(StudentDtoParam studentDto);

    // assignment to courses

    Collection<Course> receiveCoursesByStudent(String email);

    void assignCourseToStudent(int courseId, String email);

    void disassignCourseToStudent(int courseId, String email);
}
