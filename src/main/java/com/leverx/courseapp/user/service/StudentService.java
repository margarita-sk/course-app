package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import com.leverx.courseapp.user.dto.StudentOktaDto;
import com.okta.sdk.resource.user.User;
import java.util.Collection;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

  @PreAuthorize("hasAuthority('admins')")
  Collection<StudentOktaDto> receiveAll();

  //    @PreAuthorize(" @userSecurity.hasUserEmail(#authentication, #email)")
  @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
  StudentOktaDto findStudentByEmail(String email);

  User addStudent(StudentDtoRegistration studentDto);

  @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
  void deleteStudent(String email);

  // assignment to courses

  @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
  Collection<CourseDtoShort> receiveCoursesByStudent(String email);

  @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
  void assignCourseToStudent(int courseId, String email);

  @PreAuthorize("hasAuthority('admins') or #email.equals(authentication.name)")
  void disassignCourseToStudent(int courseId, String email);
}
