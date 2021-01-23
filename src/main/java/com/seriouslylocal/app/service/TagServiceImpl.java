package com.seriouslylocal.app.service;

import java.util.Optional;
import java.util.Set;

import com.seriouslylocal.app.entity.Tag;
import com.seriouslylocal.app.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository repository;

    @Override
    public Optional<Tag> findTagByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public Set<Tag> listTagsByName(String name) {
        return repository.findByNameContaining(name);
    }
    
}
