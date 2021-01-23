package com.seriouslylocal.app.repository.projection;

import java.util.Date;
import java.util.Set;

import com.seriouslylocal.app.entity.Category;
import com.seriouslylocal.app.entity.ImageInfo;
import com.seriouslylocal.app.entity.Tag;

public interface ArticleMetadata {
    public Integer getId();
    public String getTitle();
    public ImageInfo getThumbnail();
    public String getShortDescription();
    public Date getCreatedAt();
    public Date getUpdatedAt();
    public String getAddress();
    public String getPrefecture();
    public Category getCategory();
    public int getStatus();
    public Set<Tag> getTags();
}
