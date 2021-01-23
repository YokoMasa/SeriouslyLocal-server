package com.seriouslylocal.app.repository;

import com.seriouslylocal.app.entity.PrefectureInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefectureInfoRepository extends JpaRepository<PrefectureInfo, String> {
    
}
