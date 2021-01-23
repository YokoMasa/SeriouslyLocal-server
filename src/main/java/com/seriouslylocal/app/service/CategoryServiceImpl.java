package com.seriouslylocal.app.service;

import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> list() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public Category persist(Category category) {
        try {
            return repository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Category> getByName(String name) {
        return repository.findByName(name);
    }
    
}
