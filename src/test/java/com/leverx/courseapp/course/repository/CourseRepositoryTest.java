package com.leverx.courseapp.course.repository;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    private Pageable pageable;
    private List<Course> coursesExpected;

    private LocalDate date;
    private Collection<Tag> tags;
    private String name;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 1, Sort.by("name"));
        date = LocalDate.parse("2021-03-11");
        tags = List.of(new Tag(1, "cooking"));
        name = "Baking";

        var course1 = new Course("Baking cookies", "You will learn everything about cookies", LocalDate.parse("2021-02-02"), LocalDate.parse("2021-03-13"));
        course1.setId(1);
        coursesExpected = List.of(course1);
    }

    @Test
    void findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual() {
        var coursesActual = repository.findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(date, date, pageable).getContent();
        assertEquals(coursesExpected, coursesActual);
    }

    @Test
    void findCoursesByTagsIn() {
        var coursesActual = repository.findCoursesByTagsIn(tags, pageable).getContent();
        assertEquals(coursesExpected, coursesActual);
    }

    @Test
    void findCoursesByNameContains() {
        var coursesActual = repository.findCoursesByNameContains(name, pageable).getContent();
        assertEquals(coursesExpected, coursesActual);
    }
}