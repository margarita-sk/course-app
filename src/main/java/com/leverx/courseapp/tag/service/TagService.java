package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;

import java.util.Collection;
import java.util.stream.Stream;

public interface TagService {

    Tag receiveTagById(int id);

    Collection<Tag> receiveAllTags();

    Collection<Tag> receiveTagsByName(String name);

    Collection<Tag> receiveTagsByCourse(Course course);

    void addTag(Tag tag);

    void deleteTag(Tag tag);

    void editTag(Tag tag);
}
