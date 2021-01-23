package com.seriouslylocal.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

@Entity
public class ImageInfo {
    @Id
    private int id;
    private String originalImageUrl;
    private String thumbnailUrl;
    private String creditDisplay;
    private String creditUrl;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCreditDisplay() {
        return this.creditDisplay;
    }

    public void setCreditDisplay(String creditDisplay) {
        this.creditDisplay = creditDisplay;
    }

    public String getCreditUrl() {
        return this.creditUrl;
    }

    public void setCreditUrl(String creditUrl) {
        this.creditUrl = creditUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
