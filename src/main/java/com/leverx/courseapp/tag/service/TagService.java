package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.model.Tag;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface TagService {

    Tag findTagById(int id);

    Collection<Tag> findAll(Pageable paging);

    Collection<Tag> findTagsByName(String name, Pageable paging);
}
