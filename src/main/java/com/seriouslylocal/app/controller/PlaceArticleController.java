package com.seriouslylocal.app.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.seriouslylocal.app.common.HtmlUtil;
import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.repository.projection.ArticleMetadata;
import com.seriouslylocal.app.service.ArticlePreviewService;
import com.seriouslylocal.app.service.CategoryService;
import com.seriouslylocal.app.service.PlaceArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/place_article")
public class PlaceArticleController {

    @Autowired
    private PlaceArticleService service;

    @Autowired
    private ArticlePreviewService previewService;

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        model.addAttribute("articles", service.listPublic(page));
        return "place_article/index";
    }

    @GetMapping("/category/{name}")
    public String listByCategory(@RequestParam(name = "page", defaultValue = "0") int page, @PathVariable("name") String categoryName, Model model) {
        Optional<Category> op = categoryService.getByName(categoryName);
        if (op.isPresent()) {
            Category category = op.get();
            Page<ArticleMetadata> articles = service.listPublicByCategory(page, category);
            model.addAttribute("category", category);
            model.addAttribute("articles", articles);
            return "place_article/category_index";
        } else {
            return "forward:/404.html";
        }
    }

    @GetMapping("{id}")
    public String get(@PathVariable("id") int id, Model model) {
        Optional<PlaceArticle> op = service.get(id);
        if (op.isPresent()) {
            PlaceArticle article = op.get();
            if (article.getStatus() == PlaceArticle.STATUS_PRIVATE) {
                return "forward:/404.html";
            }

            model.addAttribute("article", article);
            model.addAttribute("recentArticles", service.listRecent(id));
            model.addAttribute("categories", categoryService.list());
            model.addAttribute("thumbnailCredit", HtmlUtil.createCreditHtml(article.getThumbnail()));
            model.addAttribute("preview", false);
            return "place_article/show";
        } else {
            return "forward:/404.html";
        }
    }

    @GetMapping("/preview/{id}")
    public String getPreview(@PathVariable("id") String id, Model model) {
        PlaceArticle article = previewService.getPreview(id);
        if (article == null) {
            return "forward:/404.html";
        }

        model.addAttribute("article", article);
        model.addAttribute("recentArticles", new ArrayList<>());
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("thumbnailCredit", HtmlUtil.createCreditHtml(article.getThumbnail()));
        model.addAttribute("preview", true);
        return "place_article/show";  
    }

}
