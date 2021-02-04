package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.model.Tag;
import java.util.Collection;

public interface TagService {

  Collection<Tag> findAll();

  Tag findTagById(int id);

  Collection<Tag> findTagsByName(String name);
}
