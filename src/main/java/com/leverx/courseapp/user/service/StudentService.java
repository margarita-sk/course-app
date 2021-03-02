package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;

import java.util.Collection;

public interface StudentService {

    Collection<StudentDto> receiveAll(Integer pageNo, Integer pageSize, String sortBy);

    StudentDto findStudentByEmail(String email);

    void deleteStudent(String email);

    StudentDto registerStudentInDb(StudentDto studentDto);

    // assignment to courses

    Collection<CourseDtoShort> receiveCoursesByStudent(String email);

    void assignCourseToStudent(int courseId, String email);

    void disassignCourseToStudent(int courseId, String email);
}
