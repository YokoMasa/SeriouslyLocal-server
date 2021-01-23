package com.seriouslylocal.app.controller;

import java.util.List;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/category")
public class AdminCategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> list() {
        return service.list();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody Category category) {
        Category created = service.persist(category);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

}
