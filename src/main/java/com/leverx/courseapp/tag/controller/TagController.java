package com.leverx.courseapp.tag.controller;

import com.leverx.courseapp.course.dto.CourseDtoShort;
import com.leverx.courseapp.tag.dto.TagDto;
import com.leverx.courseapp.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/tags", produces = "application/json")
public class TagController {

    private TagService tagService;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto receiveTagById(@PathVariable int id) {
        var tag = tagService.findTagById(id);
        return tag;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Collection<TagDto>> findTags(@RequestParam(defaultValue = "0") Integer pageNo,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(defaultValue = "id") String sortBy,
                                                       @RequestParam(required = false) String name) {
        var tags = tagService.findByParams(pageNo, pageSize, sortBy, name);
        return new ResponseEntity<Collection<TagDto>>(tags, new HttpHeaders(), HttpStatus.OK);
    }
}
