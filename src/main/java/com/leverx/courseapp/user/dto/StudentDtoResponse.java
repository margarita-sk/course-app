package com.leverx.courseapp.user.dto;

import com.leverx.courseapp.course.dto.CourseDtoResponse;

import java.util.Collection;

import lombok.*;

@Data
@AllArgsConstructor
public class StudentDtoResponse {

    private String email;

    private String firstName;

    private String lastName;

    private String faculty;

    private Collection<CourseDtoResponse> courses;
}
