package com.seriouslylocal.app.repository;

import java.util.Optional;

import com.seriouslylocal.app.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    public Optional<Category> findByName(String name);

}
