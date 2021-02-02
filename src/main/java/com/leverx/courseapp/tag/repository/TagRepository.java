package com.leverx.courseapp.tag.repository;

import com.leverx.courseapp.tag.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.Collection;


public interface TagRepository {

    Collection<Tag> receiveTags();

    Tag receiveTagById(int id);

    void addTag(Tag tag);

    void editCourse(Tag tag);

    void deleteCourse(Tag tag);

}
