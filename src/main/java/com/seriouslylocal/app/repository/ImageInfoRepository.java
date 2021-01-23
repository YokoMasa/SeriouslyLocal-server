package com.seriouslylocal.app.repository;

import com.seriouslylocal.app.entity.ImageInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Integer> {
    
}
