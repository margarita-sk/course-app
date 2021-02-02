package com.leverx.courseapp.tag.service;

import com.leverx.courseapp.course.model.Course;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagRepository repository;

    @Override
    public Tag receiveTagById(int id) {
        var tag = repository.receiveTagById(id);
        return tag;
    }

    @Override
    public Collection<Tag> receiveAllTags() {
        var tags = repository.receiveTags();
        return tags;
    }

    @Override
    public Collection<Tag> receiveTagsByName(String name) {
        var pattern = Pattern.compile(name);
        var tags = repository.receiveTags().stream().filter(tag -> pattern.matcher(tag.getName()).matches()).collect(Collectors.toList());
        return tags;
    }

    @Override
    public Collection<Tag> receiveTagsByCourse(Course searchedCourse) {
        var tags = repository.receiveTags().stream().filter(tag -> {
            return tag.getCourses().stream().anyMatch(course -> course.equals(searchedCourse));
        }).collect(Collectors.toList());
        return tags;
    }

    @Override
    public void addTag(Tag tag) {
        repository.addTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        repository.deleteCourse(tag);
    }

    @Override
    public void editTag(Tag tag) {
        repository.editCourse(tag);
    }
}
