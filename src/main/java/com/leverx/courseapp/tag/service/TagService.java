package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.dto.TagDto;
import java.util.Collection;

public interface TagService {

  Collection<TagDto> findAll();

  TagDto findTagById(int id);

  Collection<TagDto> findTagsByName(String name);
}
