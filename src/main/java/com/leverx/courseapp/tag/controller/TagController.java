package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.tag.dto.TagDto;
import com.leverx.courseapp.tag.model.Tag;
import com.leverx.courseapp.tag.service.TagService;
import com.leverx.courseapp.validator.Sorting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@AllArgsConstructor
@RestController
@Api(value = "tags")
@ApiResponses(
        value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 404, message = "Not Found"),
                @ApiResponse(code = 500, message = "Internal Server Error")
        })
@Validated
@RequestMapping(value = "/tags", produces = "application/json")
public class TagController {

    private final TagService tagService;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto receiveTagById(@PathVariable @Min(0) int id) {
        var tag = tagService.findTagById(id);
        return transformTagIntoDto(tag);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Collection<TagDto>> findTags(@RequestParam(defaultValue = "0") @Min(0) Integer pageNo,
                                                       @RequestParam(defaultValue = "10") @Min(1) Integer pageSize,
                                                       @RequestParam(defaultValue = "id") @Sorting String sortBy,
                                                       @RequestParam(required = false) String name) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Collection<Tag> tags;
        if (name != null) {
            tags = tagService.findTagsByName(name, paging);
        } else {
            tags = tagService.findAll(paging);
        }
        var tagsDto = transformIntoDto(tags);
        return new ResponseEntity<Collection<TagDto>>(tagsDto, new HttpHeaders(), HttpStatus.OK);
    }

    private Collection<TagDto> transformIntoDto(Collection<Tag> tags) {
        var tagsDto = tags.stream().map(tag -> new TagDto(tag.getName())).collect(Collectors.toList());
        return tagsDto;
    }

    private TagDto transformTagIntoDto(Tag tag) {
        var tagDto = new TagDto(tag.getName());
        return tagDto;
    }
}
