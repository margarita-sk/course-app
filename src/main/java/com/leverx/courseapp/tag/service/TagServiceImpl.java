package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.dto.TagDto;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.repository.TagRepository;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public TagDto findTagById(int id) {
        var tag = repository.findTagById( id);
        var tagsDto = new TagDto(tag.getName());
        return tagsDto;
    }

    @Override
    public Collection<TagDto> findByParams(Integer pageNo, Integer pageSize, String sortBy, String name) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        if (name != null) {
            return findTagsByName(name, paging);
        } else {
            return findAll(paging);
        }
    }

    @Override
    public Collection<TagDto> findAll(Pageable paging) {
        var tags =
                StreamSupport.stream(repository.findAll(paging).spliterator(), false)
                        .map(tag -> new TagDto(tag.getName()))
                        .collect(Collectors.toList());
        return tags;
    }

    @Override
    public Collection<TagDto> findTagsByName(String name, Pageable paging) {
        var tags = repository.findTagsByNameContains(name, paging);
        var tagsDto = tags.stream().map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return tagsDto;
    }
}
