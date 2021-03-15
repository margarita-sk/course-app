package com.leverx.courseapp.tag.service;


import com.leverx.courseapp.tag.exception.TagNotFoundException;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagRepository tagRepository;

    @Mock
    private Tag tag;

    @Mock
    private PageRequest paging;

    @BeforeEach
    void setUp() {
    }

    @Test
    void returnTag_whenFindTagById_givenId() {
        final int tagId = tag.getId();
        when(tagRepository.findTagById(tagId)).thenReturn(tag);

        var actualTag = tagService.findTagById(tagId);

        assertEquals(tag, actualTag);
    }

    @Test
    void throwTagNotFoundException_whenFindTagById_givenId() {
        final int tagId = 99;
        when(tagRepository.findTagById(tagId)).thenThrow(TagNotFoundException.class);

        assertThrows(TagNotFoundException.class, () -> tagService.findTagById(tagId));
    }

    @Test
    void returnTags_whenFindAll() {
        var tags = List.of(tag);
        var tagPage = new PageImpl<Tag>(tags);
        when(tagRepository.findAll(paging)).thenReturn(tagPage);

        var actualTags = tagService.findAll(paging);

        assertEquals(tags, actualTags);
    }

    @Test
    void returnTags_whenFindTagsByName_givenName() {
        var tagName = tag.getName();
        var tags = List.of(tag);
        var tagPage = new PageImpl<Tag>(tags);
        when(tagRepository.findTagsByNameContains(tagName, paging)).thenReturn(tagPage);

        var actualTags = tagService.findTagsByName(tagName, paging);

        assertEquals(tags, actualTags);
    }

    @Test
    void throwTagNotFoundException_whenFindTagsByName_givenName() {
        var tagName = "no such name";
        var tags = List.of(tag);
        var tagPage = new PageImpl<Tag>(tags);
        when(tagRepository.findTagsByNameContains(tagName, paging)).thenThrow(TagNotFoundException.class);

        assertThrows(TagNotFoundException.class, () -> tagService.findTagsByName(tagName, paging));
    }
}