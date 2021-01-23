package com.seriouslylocal.app.entity;

public class ArticleQuery {

    public static final int STATUS_ALL = 0;
    public static final int STATUS_ONLY_PUBlIC = 1;

    private int status;
    private String prefecture;
    private Category category;
    private int pageSize;
    private int page;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrefecture() {
        return this.prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "{" +
            " status='" + getStatus() + "'" +
            ", prefecture='" + getPrefecture() + "'" +
            ", category='" + getCategory() + "'" +
            ", pageSize='" + getPageSize() + "'" +
            ", page='" + getPage() + "'" +
            "}";
    }
    
}
