package com.seriouslylocal.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PrefectureInfo {
    
    @Id
    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "image_info_id")
    private ImageInfo hero;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageInfo getHero() {
        return this.hero;
    }

    public void setHero(ImageInfo hero) {
        this.hero = hero;
    }

}
