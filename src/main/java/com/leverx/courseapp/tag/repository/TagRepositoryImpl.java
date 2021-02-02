package com.leverx.courseapp.tag.repository;

import com.leverx.courseapp.tag.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class TagRepositoryImpl implements  TagRepository{
    @Override
    public Collection<Tag> receiveTags() {
        return null;
    }

    @Override
    public Tag receiveTagById(int id) {
        return null;
    }

    @Override
    public void addTag(Tag tag) {

    }

    @Override
    public void editCourse(Tag tag) {

    }

    @Override
    public void deleteCourse(Tag tag) {

    }
}
