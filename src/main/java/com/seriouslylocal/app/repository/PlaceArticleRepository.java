package com.seriouslylocal.app.repository;

import java.util.List;
import java.util.Set;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.repository.projection.ArticleMetadata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlaceArticleRepository extends JpaRepository<PlaceArticle, Integer>, JpaSpecificationExecutor<PlaceArticle> {

    public Set<ArticleMetadata> findBy();

    public Page<ArticleMetadata> findBy(Pageable pageable);

    public Page<ArticleMetadata> findAllByStatusAndCategory(Pageable pageable, int status, Category category);

    public Page<ArticleMetadata> findAllByStatus(Pageable pageable, int status);

    public List<ArticleMetadata> findAllByStatusAndIdIsNot(Pageable pageable, int status, int id);
    
}
