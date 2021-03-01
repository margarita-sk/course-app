package com.leverx.courseapp.user.service;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.course.exception.NoSuchCourseException;
import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.course.repository.CourseRepository;
import com.leverx.courseapp.logging.annotations.DbChangeable;
import com.leverx.courseapp.user.dto.StudentDtoRegistration;
import com.leverx.courseapp.user.dto.StudentDtoShort;
import com.leverx.courseapp.user.dto.StudentOktaDto;
import com.leverx.courseapp.user.exception.NoSuchStudentException;
import com.leverx.courseapp.user.model.Student;
import com.leverx.courseapp.user.repository.StudentRepository;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public Collection<StudentOktaDto> receiveAll() {
    var studentsDto =
        receiveStudentsBothDbOkta().stream()
            .map(
                user -> {
                  var profile = user.getProfile();
                  var dbStudent = receiveStudentByEmail(profile.getEmail());
                  var faculty = dbStudent.getFaculty();
                  var courses = dbStudent.getCourses();
                  return new StudentOktaDto(
                      profile.getEmail(),
                      profile.getFirstName(),
                      profile.getLastName(),
                      faculty,
                      courses);
                })
            .collect(Collectors.toList());
    return studentsDto;
  }

  @Override
  public StudentOktaDto findStudentByEmail(String email) {
    var studentFromDb = studentRepository.findById(email).orElseThrow(NoSuchStudentException::new);
    var studentFromOkta =
        receiveStudentsBothDbOkta().stream()
            .filter(user -> email.equals(user.getProfile().getEmail()))
            .findFirst()
            .orElseThrow(NoSuchStudentException::new);
    var studentOktaDto =
        new StudentOktaDto(
            studentFromOkta.getProfile().getEmail(),
            studentFromOkta.getProfile().getFirstName(),
            studentFromOkta.getProfile().getLastName(),
            studentFromDb.getFaculty(),
            studentFromDb.getCourses());
    return studentOktaDto;
  }

  @Override
  public Collection<CourseDtoShort> receiveCoursesByStudent(String email) {
    var student = studentRepository.findById(email).orElseThrow((NoSuchStudentException::new));
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
    var student = studentRepository.findById(email).orElseThrow(NoSuchStudentException::new);
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
  public StudentOktaDto registerStudentInDb(StudentDtoShort studentDto) {
    studentRepository.save(new Student(studentDto.getEmail(), studentDto.getFaculty()));
    var student = findStudentByEmail(studentDto.getEmail());
    return student;
  }

  @Override
  public void assignCourseToStudent(int courseId, String email) {
    var student = receiveStudentByEmail(email);
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
    var student = receiveStudentByEmail(email);
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
            .orElseThrow(
                () -> {
                  throw new NoSuchCourseException(id);
                });
    return course;
  }

  private Student receiveStudentByEmail(String email) {
    var student =
        studentRepository
            .findById(email)
            .orElseThrow(
                () -> {
                  throw new NoSuchStudentException(email);
                });
    return student;
  }

  private List<User> receiveStudentsBothDbOkta() {
    var studentsFromOktaStream = client.listUsers().stream();
    var studentsFromDB =
        StreamSupport.stream(studentRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    var students =
        studentsFromOktaStream
            .filter(
                user ->
                    studentsFromDB.stream()
                        .anyMatch(
                            student -> student.getEmail().equals(user.getProfile().getEmail())))
            .collect(Collectors.toList());
    return students;
  }
}
