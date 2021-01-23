package com.seriouslylocal.app.controller;

import java.util.Optional;

import com.seriouslylocal.app.entity.PrefectureInfo;
import com.seriouslylocal.app.service.PrefectureInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/prefecture")
public class AdminPrefectureInfoController {
    
    @Autowired
    private PrefectureInfoService service;

    @GetMapping("/{name}")
    public Optional<PrefectureInfo> get(@PathVariable("name") String name) {
        return service.get(name);
    }

    @PostMapping
    public ResponseEntity<PrefectureInfo> persist(PrefectureInfo prefectureInfo) {
        PrefectureInfo saved = service.persist(prefectureInfo);
        if (saved != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
