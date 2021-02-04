package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;
import java.util.Collection;
import java.util.TreeSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository repository;

  @Override
  public Collection<Tag> findAll() {
    var tags = new TreeSet<Tag>();
    repository.findAll().iterator().forEachRemaining(tag -> tags.add(tag));
    return tags;
  }

  @Override
  public Tag findTagById(int id) {
    var tag = repository.findById(id);
    return tag.orElseThrow(TagNotFoundException::new);
  }

  @Override
  public Collection<Tag> findTagsByName(String name) {
    var tags = repository.findTagsByNameContains(name);
    return tags;
  }
}
