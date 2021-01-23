package com.seriouslylocal.app.service;

import java.util.Optional;
import java.util.Set;

import com.seriouslylocal.app.entity.Tag;

public interface TagService {

    public Optional<Tag> findTagByName(String name);

    public Set<Tag> listTagsByName(String name);
    
}
