package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface StudentService {


    Collection<CourseDtoShort> receiveCoursesByStudent(String studentName);

    @PreAuthorize("hasAuthority('admins')")
    Collection<StudentDtoShort> receiveAll();

    @PreAuthorize("hasAuthority('admins') or #user.name == #studentName")
    StudentDto receiveStudentByName(String studentName, OidcUser user);

    @PreAuthorize("hasAuthority('admins')")
    void assignCourseToStudent(int courseId, String studentName);

    @PreAuthorize("hasAuthority('admins')")
    void disassignCourseToStudent(int courseId,  String studentName);

}
