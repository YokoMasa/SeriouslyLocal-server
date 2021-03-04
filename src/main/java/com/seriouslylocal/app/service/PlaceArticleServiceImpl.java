package com.seriouslylocal.app.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.seriouslylocal.app.common.ArticleCommandProcesserManager;
import com.seriouslylocal.app.common.CreditCommandProcesser;
import com.seriouslylocal.app.common.InstagramCommandProcesser;
import com.seriouslylocal.app.common.MapCommandProcesser;
import com.seriouslylocal.app.entity.ArticleQuery;
import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.entity.Tag;
import com.seriouslylocal.app.repository.PlaceArticleRepository;
import com.seriouslylocal.app.repository.TagRepository;
import com.seriouslylocal.app.repository.projection.ArticleMetadata;
import com.seriouslylocal.app.repository.specification.PlaceArticleSpecs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PlaceArticleServiceImpl implements PlaceArticleService {

    private static final int PAGE_SIZE = 25;
    private static final Sort CREATED_AT_SORT = Sort.by(Direction.DESC, "createdAt");

    @Autowired
    private PlaceArticleRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CreditCommandProcesser creditCommandProcesser;

    @Autowired
    private InstagramCommandProcesser instagramCommandProcesser;

    @Autowired
    private MapCommandProcesser mapCommandProcesser;

    private ArticleCommandProcesserManager commandProcesserManager;

    @PostConstruct
    public void init() {
        commandProcesserManager = new ArticleCommandProcesserManager();
        commandProcesserManager.addProcesser(creditCommandProcesser);
        commandProcesserManager.addProcesser(mapCommandProcesser);
        commandProcesserManager.addProcesser(instagramCommandProcesser);
    }

    @Override
    public Page<ArticleMetadata> list(int page) {
        return repository.findBy(PageRequest.of(page, PAGE_SIZE, CREATED_AT_SORT));
    }

    @Override
    public Page<ArticleMetadata> listPublic(int page) {
        return repository.findAllByStatus(PageRequest.of(page, PAGE_SIZE, CREATED_AT_SORT), PlaceArticle.STATUS_PUBLIC);
    }

    @Override
    public Page<ArticleMetadata> listPublicByCategory(int page, Category category) {
        return repository.findAllByStatusAndCategory(PageRequest.of(page, PAGE_SIZE, CREATED_AT_SORT),
                PlaceArticle.STATUS_PUBLIC, category);
    }

    @Override
    public List<ArticleMetadata> listRecent(int excludeId) {
        return repository.findAllByStatusAndIdIsNot(PageRequest.of(0, 5, CREATED_AT_SORT), PlaceArticle.STATUS_PUBLIC,
                excludeId);
    }

    @Override
    public Optional<PlaceArticle> get(int id) {
        return repository.findById(id);
    }

    @Override
    public PlaceArticle persist(PlaceArticle placeArticle) {
        try {
            persistNewTags(placeArticle);
            if (placeArticle.getCreatedAt() == null) {
                placeArticle.setCreatedAt(new Date());
            }
            placeArticle.setContent(commandProcesserManager.process(placeArticle.getRawContent()));
            placeArticle.setUpdatedAt(new Date());
            return repository.saveAndFlush(placeArticle);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void persistNewTags(PlaceArticle placeArticle) {
        Set<Tag> unsavedNewTags = placeArticle.getTags().stream().filter(tag -> tag.getId() == null)
                .collect(Collectors.toSet());
        placeArticle.getTags().removeAll(unsavedNewTags);

        Set<Tag> savedNewTags = unsavedNewTags.stream().map(tagRepository::save).collect(Collectors.toSet());
        placeArticle.getTags().addAll(savedNewTags);
    }

    @Override
    public Page<PlaceArticle> list(ArticleQuery q) {
        Specification<PlaceArticle> spec = PlaceArticleSpecs.isPublic();

        if (q.getCategory() != null) {
            spec = spec.and(PlaceArticleSpecs.isInCategory(q.getCategory()));
        }

        if (q.getPrefecture() != null) {
            spec = spec.and(PlaceArticleSpecs.isInPrefecture(q.getPrefecture()));
        }

        Pageable pageable = null;
        if (q.getPageSize() != 0) {
            pageable = PageRequest.of(q.getPage(), q.getPageSize(), CREATED_AT_SORT);
        } else {
            pageable = PageRequest.of(0, 25, CREATED_AT_SORT);
        }

        Page<PlaceArticle> articles = repository.findAll(spec, pageable);
            
        articles.forEach(article -> { 
            article.setContent(null);
            article.setRawContent(null); 
        });
        return articles;
    }
    
}
