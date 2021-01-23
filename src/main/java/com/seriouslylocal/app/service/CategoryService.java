package com.seriouslylocal.app.service;

import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.entity.Category;

public interface CategoryService {

    public Optional<Category> getByName(String name);

    public List<Category> list();

    public Category persist(Category category);
    
}
