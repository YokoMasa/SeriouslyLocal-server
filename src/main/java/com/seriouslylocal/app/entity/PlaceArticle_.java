package com.seriouslylocal.app.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PlaceArticle.class)
public abstract class PlaceArticle_ {

	public static volatile SingularAttribute<PlaceArticle, ImageInfo> thumbnail;
	public static volatile SingularAttribute<PlaceArticle, String> address;
	public static volatile SingularAttribute<PlaceArticle, String> prefecture;
	public static volatile SingularAttribute<PlaceArticle, String> shortDescription;
	public static volatile SingularAttribute<PlaceArticle, String> title;
	public static volatile SingularAttribute<PlaceArticle, String> content;
	public static volatile SingularAttribute<PlaceArticle, String> rawContent;
	public static volatile SetAttribute<PlaceArticle, Tag> tags;
	public static volatile SingularAttribute<PlaceArticle, Date> createdAt;
	public static volatile SingularAttribute<PlaceArticle, Integer> id;
	public static volatile SingularAttribute<PlaceArticle, Category> category;
	public static volatile SingularAttribute<PlaceArticle, Date> updatedAt;
	public static volatile SingularAttribute<PlaceArticle, Integer> status;

	public static final String THUMBNAIL = "thumbnail";
	public static final String ADDRESS = "address";
	public static final String PREFECTURE = "prefecture";
	public static final String SHORT_DESCRIPTION = "shortDescription";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String RAW_CONTENT = "rawContent";
	public static final String TAGS = "tags";
	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String UPDATED_AT = "updatedAt";
	public static final String STATUS = "status";

}

