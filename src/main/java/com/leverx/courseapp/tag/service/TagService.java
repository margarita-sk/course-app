package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.dto.TagDto;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface TagService {

    TagDto findTagById(int id);

    Collection<TagDto> findByParams(Integer pageNo, Integer pageSize, String sortBy, String name);

    Collection<TagDto> findAll(Pageable paging);

    Collection<TagDto> findTagsByName(String name, Pageable paging);
}
