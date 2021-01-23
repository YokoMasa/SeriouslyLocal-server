package com.seriouslylocal.app.controller;

import java.util.List;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class ApiCategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping
    public List<Category> list() {
        return service.list();
    }

}
