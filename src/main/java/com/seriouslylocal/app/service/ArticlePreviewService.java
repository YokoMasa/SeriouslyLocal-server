package com.seriouslylocal.app.service;

import javax.annotation.PostConstruct;

import com.seriouslylocal.app.common.ArticleCommandProcesserManager;
import com.seriouslylocal.app.common.CreditCommandProcesser;
import com.seriouslylocal.app.common.InstagramCommandProcesser;
import com.seriouslylocal.app.common.MapCommandProcesser;
import com.seriouslylocal.app.entity.PlaceArticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ArticlePreviewService {

    private String id;
    private PlaceArticle article;

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
    
    public String createPreview(PlaceArticle article) {
        String processedContent = commandProcesserManager.process(article.getRawContent());
        article.setContent(processedContent);
        this.article = article;
        this.id = Long.toString(System.currentTimeMillis());
        return id;
    }

    public PlaceArticle getPreview(String id) {
        if (this.id != null && this.id.equals(id)) {
            PlaceArticle a = this.article;
            this.id = "";
            this.article = null;
            return a;
        } else {
            return null;
        }
    }

}
