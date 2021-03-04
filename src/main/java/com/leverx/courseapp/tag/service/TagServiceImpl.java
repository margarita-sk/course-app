package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public Tag findTagById(int id) {
        var tag = repository.findTagById(id);
        return tag;
    }

    @Override
    public Collection<Tag> findAll(Pageable paging) {
        var tagsPag = repository.findAll(paging);
        return tagsPag.getContent();
    }

    @Override
    public Collection<Tag> findTagsByName(String name, Pageable paging) {
        var tagsPage = repository.findTagsByNameContains(name, paging);
        return tagsPage.getContent();
    }
}
