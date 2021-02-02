package com.leverx.courseapp.course.repository;

import com.leverx.courseapp.course.model.Course;

import java.util.Collection;


public interface CourseRepository {

    Collection<Course> receiveAllCourses();

    Course receiveCourseById(int id);

    void addCourse(Course course);

    void editCourse(Course course);

    void deleteCourse(int id);

}
