package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.service.TagService;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @GetMapping("/{id}")
    public Tag receiveTagById(@PathVariable int id) {
        var tag = tagService.findTagById(id);
        return tag;
    }

    @GetMapping("")
    public Collection<Tag> receiveAllTags(@RequestParam(required = false) String name) {
        if (name != null) {
            return tagService.findTagsByName(name);
        }
        return tagService.findAll();
    }
}
