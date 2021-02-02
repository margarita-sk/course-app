package com.leverx.courseapp.course.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.task.model.Task;
import com.leverx.courseapp.tag.model.Tag;

import java.time.LocalDate;
import java.util.Collection;

public interface CourseService {

    Course receiveCourseById(int id);

    Collection<Course> receiveAllCourses();

    Collection<Course> receiveCoursesByDate(LocalDate date);

    Collection<Course> receiveCoursesByTags(Collection<Tag> tags);

    Collection<Course> receiveCoursesByName(String name);

    void addCourse(Course course);

    void deleteCourse(int id);

    void editCourse(Course course);




}
