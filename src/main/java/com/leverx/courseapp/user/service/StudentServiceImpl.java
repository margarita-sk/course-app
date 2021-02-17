package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder encoder;

    @Override
    public Collection<StudentDtoShort> receiveAll() {
        var students =
                StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                        .map(student -> new StudentDtoShort(student.getName(), student.getFaculty()))
                        .collect(Collectors.toList());
        return students;
    }

    @Override
    public StudentDto receiveStudentByName(String studentName, OidcUser user) {
        var student = studentRepository.findByNameContains(studentName);
//        if(user.getName() == studentName) {
//            return new StudentDto(student.getName(), student.getFaculty(), user.getEmail());
//        }
        return new StudentDto(student.getName(), student.getFaculty());
    }

    @Override
    public Collection<CourseDtoShort> receiveCoursesByStudent(String studentName) {
        var student = studentRepository.findByNameContains(studentName);
        var courses = student.getCourses();
        var coursesDto = courses.stream().map(course -> new CourseDtoShort(course.getName(), course.getDescription())).collect(Collectors.toList());
        return coursesDto;
    }

    @Override
    public void assignCourseToStudent(int courseId, String studentName) {
        var student = studentRepository.findByNameContains(studentName);
        var newCourse = receiveCourseById(courseId);
        var studentsCourses = student.getCourses();
        studentsCourses.add(newCourse);
        student.setCourses(studentsCourses);
        studentRepository.save(student);
    }

    @Override
    public void disassignCourseToStudent(int courseId, String studentName) {
        var student =  studentRepository.findByNameContains(studentName);
        var courseToRemove = receiveCourseById(courseId);
        var studentsCourses = student.getCourses();
        studentsCourses.remove(courseToRemove);
        student.setCourses(studentsCourses);
        studentRepository.save(student);
    }


    private Course receiveCourseById(int id){
        var course = courseRepository.findById(id).orElseThrow(() -> {
            throw new NoSuchCourseException(id);
        });
        return course;
    }

}
