package com.seriouslylocal.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.seriouslylocal.app.ProfileConstant;
import com.seriouslylocal.app.entity.ImageInfo;
import com.seriouslylocal.app.repository.ImageInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component("CloudStorageImageService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile(ProfileConstant.PRODUCTION)
public class CloudStorageImageService implements ImageService {

    private static final int PAGE_SIZE = 18;
    private static final String THUMBNAIL_PREFIX = "thumbnail_";

    @Value("${imageService.credentialFilePath}")
    private String storageCredentialFilePath;

    @Value("${imageService.originalImageBucketName}")
    private String originalImageBucketName;

    @Value("${imageService.thumbnailBucketName}")
    private String thumbnailBucketName;

    @Autowired
    private ImageInfoRepository repository;

    private Storage storage;

    @PostConstruct
    public void init() throws IOException {
        if (storageCredentialFilePath == null) {
            throw new RuntimeException("set imageService.credentialFilePath in your property file.");
        }

        InputStream is = new ClassPathResource(storageCredentialFilePath).getInputStream();
        storage = StorageOptions.newBuilder()
            .setCredentials(ServiceAccountCredentials.fromStream(is))
            .build()
            .getService();
    }

    @Override
    public ImageInfo save(InputStream original, InputStream thumbnail, String fileName, String creditDisplay, String creditUrl) throws IOException {
        String thumbnailFileName = THUMBNAIL_PREFIX + fileName;
        BlobInfo thumbnailBlobInfo = BlobInfo.newBuilder(thumbnailBucketName, thumbnailFileName).build();
        Blob thumbnailBlob = storage.createFrom(thumbnailBlobInfo, thumbnail);
        String thumbnailUrl = thumbnailBlob.getMediaLink();

        BlobInfo blobInfo = BlobInfo.newBuilder(originalImageBucketName, fileName).build();
        Blob blob = storage.createFrom(blobInfo, original);
        String originalUrl = blob.getMediaLink();

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setOriginalImageUrl(originalUrl);
        imageInfo.setThumbnailUrl(thumbnailUrl);
        imageInfo.setCreditDisplay(creditDisplay);
        imageInfo.setCreditUrl(creditUrl);
        return repository.save(imageInfo);
    }

    @Override
    public List<ImageInfo> list(int page) {
        Page<ImageInfo> imageInfoPage = repository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending()));
        return imageInfoPage.toList();
    }

}
