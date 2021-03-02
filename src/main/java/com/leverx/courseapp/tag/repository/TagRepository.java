package com.leverx.courseapp.tag.repository;

import com.leverx.courseapp.tag.model.Tag;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    Page<Tag> findTagsByNameContains(String name, Pageable paging);

    Collection<Tag> findTagsByNameContains(String name);

    Tag findTagById(int id);
}
