package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.dto.StudentOktaDto;
import com.okta.sdk.resource.user.User;
import java.util.Collection;

public interface StudentService {

  Collection<StudentOktaDto> receiveAll();

  StudentOktaDto findStudentByEmail(String email);

  void deleteStudent(String email);

  StudentOktaDto registerStudentInDb(StudentDtoShort studentDto);

  // assignment to courses

  Collection<CourseDtoShort> receiveCoursesByStudent(String email);

  void assignCourseToStudent(int courseId, String email);

  void disassignCourseToStudent(int courseId, String email);
}
