package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.DbChangeable;
import com.leverx.courseapp.user.dto.StudentDto;
import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import com.okta.sdk.client.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final Client client;

    @Value("${okta.group.users.id}")
    private String groupId;

    public StudentServiceImpl(
            StudentRepository studentRepository, CourseRepository courseRepository, Client client) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.client = client;
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
        studentRepository.delete(student);
        var user =
                client.listUsers().stream()
                        .filter(searchedUser -> searchedUser.getProfile().getEmail().equals(email))
                        .findFirst()
                        .orElseThrow(NoSuchStudentException::new);
        user.deactivate();
        user.delete();
    }

    @Override
    public StudentDto registerStudentInDb(StudentDto studentDto) {
        studentRepository.save(new Student(studentDto.getEmail(), studentDto.getFirstName(), studentDto.getLastName(), studentDto.getFaculty()));
        var student = findStudentByEmail(studentDto.getEmail());
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
