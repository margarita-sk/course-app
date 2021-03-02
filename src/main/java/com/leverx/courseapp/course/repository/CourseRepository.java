package com.leverx.courseapp.course.repository;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Collection;


public interface CourseRepository extends PagingAndSortingRepository<Course, Integer> {

    Page<Course> findCoursesByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(LocalDate date1, LocalDate date2, Pageable pageable);

    Page<Course> findCoursesByTagsIn(Collection<Tag> tags, Pageable pageable);

    Page<Course> findCoursesByNameContains(String name, Pageable pageable);
}
