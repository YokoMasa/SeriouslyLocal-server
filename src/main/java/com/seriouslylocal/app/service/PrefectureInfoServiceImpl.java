package com.seriouslylocal.app.service;

import java.util.Optional;

import com.seriouslylocal.app.entity.PrefectureInfo;
import com.seriouslylocal.app.repository.PrefectureInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrefectureInfoServiceImpl implements PrefectureInfoService {

    @Autowired
    private PrefectureInfoRepository repository;

    @Override
    public Optional<PrefectureInfo> get(String name) {
        return repository.findById(name);
    }

    @Override
    public PrefectureInfo persist(PrefectureInfo prefectureInfo) {
        try {
            return repository.save(prefectureInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
