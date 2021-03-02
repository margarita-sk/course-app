package com.leverx.courseapp.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leverx.courseapp.course.model.Course;

import java.util.Collection;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentDto {

    @NonNull
    private String email;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String faculty;

    @JsonIgnore
    private Collection<Course> courses;
}
