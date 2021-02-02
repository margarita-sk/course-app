package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TagController {


    @GetMapping("/tag/{id}")
    public Tag receiveTagById(@PathVariable int id){
        return  null;
    }

    @GetMapping("/tags")
    public Collection<Tag> receiveAllTags(){
        return null;
    }

    @GetMapping("/tags/{name}")
    public Collection<Tag> receiveTagsByName(@PathVariable String name){
        return null;
    }

    @GetMapping("/tags/{courses}")
    public Collection<Tag> receiveTagsByCourse(@PathVariable Course course){
        return null;
    }

    @PostMapping("/tag")
    public void addTag(@PathVariable Tag tag){

    }

    @DeleteMapping("/tag/{id}")
    public void deleteTag (@PathVariable int id){

    }

    @PutMapping("/tag/{id}")
    public void editTag (@PathVariable Tag tag){

    }
}
