package com.seriouslylocal.app.service;

import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.entity.ArticleQuery;
import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.repository.projection.ArticleMetadata;

import org.springframework.data.domain.Page;

public interface PlaceArticleService {
    
    public Page<PlaceArticle> list(ArticleQuery q);

    public Page<ArticleMetadata> list(int page);

    public Page<ArticleMetadata> listPublic(int page);

    public Page<ArticleMetadata> listPublicByCategory(int page, Category category);

    public List<ArticleMetadata> listRecent(int excludeId);

    public Optional<PlaceArticle> get(int id);

    public PlaceArticle persist(PlaceArticle placeArticle);

    public void delete(int id);

}
