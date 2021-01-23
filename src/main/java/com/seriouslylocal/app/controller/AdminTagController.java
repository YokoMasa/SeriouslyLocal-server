package com.seriouslylocal.app.controller;

import java.util.Optional;
import java.util.Set;

import com.seriouslylocal.app.entity.Tag;
import com.seriouslylocal.app.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/tag")
public class AdminTagController {

    @Autowired
    private TagService service;

    @GetMapping("/{name}")
    public Optional<Tag> findTagByName(@PathVariable("name") String name) {
        return service.findTagByName(name);
    }

    @GetMapping
    public Set<Tag> listTagsByName(@RequestParam("q") String q) {
        return service.listTagsByName(q);
    }
    
}
