package com.seriouslylocal.app.service;

import java.util.Optional;

import com.seriouslylocal.app.entity.PrefectureInfo;

public interface PrefectureInfoService {
    
    public Optional<PrefectureInfo> get(String name);

    public PrefectureInfo persist(PrefectureInfo prefectureInfo);

}
