package com.leverx.courseapp.course.repository;

import com.leverx.courseapp.course.model.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
@AllArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

    private EntityManager entityManager;

    @Override
    public Collection<Course> receiveAllCourses() {

        return null;
    }

    @Override
    public Course receiveCourseById(int id) {
        var course = entityManager.find(Course.class, id);
        return course;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void editCourse(Course course) {

    }

    @Override
    public void deleteCourse(int id) {

    }
}
