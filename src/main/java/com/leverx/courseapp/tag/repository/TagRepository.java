package com.leverx.courseapp.tag.repository;

import com.leverx.courseapp.tag.model.Tag;
import java.util.Collection;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {

  Collection<Tag> findTagsByNameContains(String name);
}
