package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.Changeable;
import com.leverx.courseapp.user.dto.StudentDtoParam;
import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import com.leverx.courseapp.user.repository.UserClient;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final UserClient client;

    @Override
    public Collection<Student> receiveAll(Integer pageNo, Integer pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var students = studentRepository.findAll(paging).getContent();
        return students;
    }

    @Override
    public Student findStudentByEmail(String email) {
        var student = studentRepository.findStudentByEmail(email);
        return student;
    }

    @Override
    public Collection<Course> receiveCoursesByStudent(String email) {
        var student = studentRepository.findStudentByEmail(email);
        var courses = student.getCourses();
        return courses;
    }

    @Override
    @Changeable
    public void deleteStudent(String email) {
        var student = studentRepository.findById(email).orElseThrow(NoSuchStudentException::new);
        client.deleteUser(email);
        studentRepository.delete(student);
    }

    @Override
    @Changeable
    public Student addStudent(StudentDtoParam studentDto) {
        var student = new Student(studentDto.getEmail(), studentDto.getFirstName(), studentDto.getLastName(), studentDto.getFaculty());
        client.addUser(studentDto);
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
