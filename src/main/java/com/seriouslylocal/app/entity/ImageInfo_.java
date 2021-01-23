package com.seriouslylocal.app.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImageInfo.class)
public abstract class ImageInfo_ {

	public static volatile SingularAttribute<ImageInfo, String> originalImageUrl;
	public static volatile SingularAttribute<ImageInfo, String> creditDisplay;
	public static volatile SingularAttribute<ImageInfo, String> creditUrl;
	public static volatile SingularAttribute<ImageInfo, Integer> id;
	public static volatile SingularAttribute<ImageInfo, String> thumbnailUrl;

	public static final String ORIGINAL_IMAGE_URL = "originalImageUrl";
	public static final String CREDIT_DISPLAY = "creditDisplay";
	public static final String CREDIT_URL = "creditUrl";
	public static final String ID = "id";
	public static final String THUMBNAIL_URL = "thumbnailUrl";

}

