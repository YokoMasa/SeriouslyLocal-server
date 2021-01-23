package com.seriouslylocal.app.controller;

import java.util.List;

import com.seriouslylocal.app.entity.ArticleQuery;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.service.PlaceArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place_article")
public class ApiPlaceArticleController {

    @Autowired
    private PlaceArticleService service;
    
    @GetMapping
    public List<PlaceArticle> list(ArticleQuery q) {
        System.out.println(q);
        return service.list(q).toList();
    }

}
