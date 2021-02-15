package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.tag.dto.TagDto;
import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.DTD;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public Collection<TagDto> findAll() {
        var tags = StreamSupport.stream(repository.findAll().spliterator(), false).map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return tags;
    }

    @Override
    public TagDto findTagById(int id) {
        var tag = repository.findById(id).orElseThrow(TagNotFoundException::new);
        var tagsDto = new TagDto(tag.getName());
        return tagsDto;
    }

    @Override
    public Collection<TagDto> findTagsByName(String name) {
        var tags = repository.findTagsByNameContains(name);
        var tagsDto = tags.stream().map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return tagsDto;
    }
}
