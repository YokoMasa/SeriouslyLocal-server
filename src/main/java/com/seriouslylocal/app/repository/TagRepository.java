package com.seriouslylocal.app.repository;

import java.util.Optional;
import java.util.Set;

import com.seriouslylocal.app.entity.Tag;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    
    public Set<Tag> findByNameContaining(String name);

    public Optional<Tag> getByName(String name);

}
