package com.seriouslylocal.app.controller;

import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.ProfileConstant;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.repository.projection.ArticleMetadata;
import com.seriouslylocal.app.service.ArticlePreviewService;
import com.seriouslylocal.app.service.PlaceArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/place_article")
public class AdminPlaceArticleController {

    @Autowired
    private Environment env;
    
    @Autowired
    private PlaceArticleService service;

    @Autowired
    private ArticlePreviewService previewService;

    @GetMapping
    public List<ArticleMetadata> list() {
        return service.list(0).toList();
    }

    @GetMapping("/{id}")
    public Optional<PlaceArticle> get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceArticle> create(@RequestBody PlaceArticle placeArticle) {
        if (!validate(placeArticle)) {
            return ResponseEntity.badRequest().build(); 
        }

        PlaceArticle persisted = service.persist(placeArticle);
        if (persisted != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(persisted);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/preview", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPreview(@RequestBody PlaceArticle placeArticle) {
        if (!validate(placeArticle)) {
            return ResponseEntity.badRequest().build(); 
        }

        String id = previewService.createPreview(placeArticle);
        
        String jsonString = "";
        if (env.acceptsProfiles(Profiles.of(ProfileConstant.PRODUCTION))) {
            jsonString = "{\"url\": \"https://seriously-local.com/place_article/preview/" + id + "\"}";
        } else {
            jsonString = "{\"url\": \"http://localhost:8080/place_article/preview/" + id + "\"}";
        }

        return ResponseEntity.status(HttpStatus.OK).body(jsonString);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private boolean validate(PlaceArticle article) {
        return StringUtils.hasText(article.getTitle()) 
                && StringUtils.hasText(article.getRawContent())
                && StringUtils.hasText(article.getShortDescription())
                && article.getCategory() != null
                && article.getThumbnail() != null;
    }

}
