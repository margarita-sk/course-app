package com.leverx.courseapp.course.repository;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {

  Collection<Course> findByStartAssignmentDateLessThanEqualAndEndAssignmentDateGreaterThanEqual(
      LocalDate date1, LocalDate date2);

  Collection<Course> findCoursesByTagsIn(Collection<Tag> tags);

  Collection<Course> findCoursesByNameContains(String name);
}
