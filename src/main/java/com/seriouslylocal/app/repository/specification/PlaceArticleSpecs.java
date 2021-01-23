package com.seriouslylocal.app.repository.specification;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.PlaceArticle;
import com.seriouslylocal.app.entity.PlaceArticle_;

import org.springframework.data.jpa.domain.Specification;

public class PlaceArticleSpecs {

    public static Specification<PlaceArticle> isInPrefecture(String prefecture) {
        return (root, query, builder) -> {
            return builder.equal(root.get(PlaceArticle_.prefecture), prefecture);
        };
    }

    public static Specification<PlaceArticle> isPublic() {
        return (root, query, builder) -> {
            return builder.equal(root.get(PlaceArticle_.status), PlaceArticle.STATUS_PUBLIC);
        };
    }

    public static Specification<PlaceArticle> isInCategory(Category category) {
        return (root, query, builder) -> {
            return builder.equal(root.get(PlaceArticle_.category), category);
        };
    }
    
}
