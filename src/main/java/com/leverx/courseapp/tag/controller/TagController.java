package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.service.TagService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TagController {

  private TagService tagService;

  @GetMapping("/tags/{id}")
  public Tag receiveTagById(@PathVariable int id) {
    var tag = tagService.findTagById(id);
    return tag;
  }

  @GetMapping("/tags")
  public Collection<Tag> receiveAllTags() {
    return null;
  }

  @GetMapping("/tags/find-by-name")
  public Collection<Tag> receiveTagsByName(@RequestParam String name) {
    var tag = tagService.findTagsByName(name);
    return tag;
  }
}
