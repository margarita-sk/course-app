package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.DbChangeable;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import com.okta.sdk.client.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final Client client;
    private final UserBuilder userBuilder;


    public StudentServiceImpl(
            StudentRepository studentRepository, CourseRepository courseRepository, Client client, UserBuilder userBuilder) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.client = client;
        this.userBuilder = userBuilder;
    }

    @Override
    public Collection<StudentDto> receiveAll(Integer pageNo, Integer pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var students = studentRepository.findAll(paging);
        var studentsDto = students.stream().map(student -> new StudentDto(student.getEmail(), student.getFirstName(), student.getLastName(), student.getFaculty(), student.getCourses())).collect(Collectors.toList());
        return studentsDto;
    }

    @Override
    public StudentDto findStudentByEmail(String email) {
        var student = studentRepository.findStudentByEmail(email);
        var studentDto = new StudentDto(student.getEmail(), student.getFirstName(), student.getLastName(), student.getFaculty(), student.getCourses());
        return studentDto;
    }

    @Override
    public Collection<CourseDtoShort> receiveCoursesByStudent(String email) {
        var student = studentRepository.findStudentByEmail(email);
        var courses = student.getCourses();
        var coursesDto =
                courses.stream()
                        .map(course -> new CourseDtoShort(course.getId(), course.getName(), course.getDescription()))
                        .collect(Collectors.toList());
        return coursesDto;
    }

    @Override
    @DbChangeable
    public void deleteStudent(String email) {
        var student = studentRepository.findStudentByEmail(email);
        var user =
                client.listUsers().stream()
                        .filter(searchedUser -> searchedUser.getProfile().getEmail().equals(email))
                        .findFirst()
                        .orElseThrow(NoSuchStudentException::new);
        user.deactivate();
        user.delete();
        studentRepository.delete(student);
    }

    @Override
    public Student addStudent(StudentDtoRegistration studentDto) {
        var student = new Student(studentDto.getEmail(), studentDto.getFirstName(), studentDto.getLastName(), studentDto.getFaculty());
        var user = userBuilder
                .setEmail(student.getEmail())
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setPassword(studentDto.getPassword())
                .buildAndCreate(client);
        studentRepository.save(student);
        return student;
    }

    @Override
    public void assignCourseToStudent(int courseId, String email) {
        var student = studentRepository.findStudentByEmail(email);
        var newCourse = receiveCourseById(courseId);
        var studentsCourses = student.getCourses();
        studentsCourses.add(newCourse);
        student.setCourses(studentsCourses);
        student = studentRepository.save(student);
        newCourse.setStudents(new ArrayList<>(newCourse.getStudents()));
        newCourse.getStudents().add(student);
        courseRepository.save(newCourse);
    }

    @Override
    public void disassignCourseToStudent(int courseId, String email) {
        var student = studentRepository.findStudentByEmail(email);
        var courseToRemove = receiveCourseById(courseId);
        var studentsCourses = student.getCourses();
        studentsCourses.remove(courseToRemove);
        student.setCourses(studentsCourses);
        studentRepository.save(student);
    }

    private Course receiveCourseById(int id) {
        var course =
                courseRepository
                        .findById(id)
                        .orElseThrow(NoSuchCourseException::new);
        return course;
    }
}
