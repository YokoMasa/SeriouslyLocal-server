package com.seriouslylocal.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.seriouslylocal.app.ProfileConstant;
import com.seriouslylocal.app.entity.ImageInfo;
import com.seriouslylocal.app.repository.ImageInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component("LocalImageService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Profile(ProfileConstant.DEV)
public class LocalImageService implements ImageService {

    private static final int PAGE_SIZE = 18;
    public static final String rootStaticPath = "/static";
    public static final String originalImageSavePath = "/images";
    public static final String thumbnailSavePath = "/thumbnails";
    public static final String thumbnailPrefix = "thumbnail_";

    @Autowired
    private ImageInfoRepository repository;

    private File rootDir;

    @Override
    public ImageInfo save(InputStream original, InputStream thumbnail, String fileName, String creditDisplay, String creditUrl) throws IOException {
        File originalImageFile = new File(getSaveDir(originalImageSavePath), fileName);
        Files.copy(original, originalImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String thumbNailFileName = thumbnailPrefix + fileName;
        File thumbnailImageFile = new File(getSaveDir(thumbnailSavePath), thumbNailFileName);
        Files.copy(thumbnail, thumbnailImageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        String originalUrl = createUrl(originalImageSavePath, fileName);
        String thumbnailUrl = createUrl(thumbnailSavePath, thumbNailFileName);

        ImageInfo imageInfo =  new ImageInfo();
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

    private String createUrl(String savePath, String fileName) {
        return "http://192.168.2.111:8080" + savePath + "/" + fileName;
        //return savePath + "/" + fileName;
    }

    private File getSaveDir(String savePath) throws IOException {
        File saveDir = new File(getRootDir(), savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        return saveDir;
    }

    private File getRootDir() throws IOException {
        if (rootDir != null) {
            return rootDir;
        }
        rootDir = new ClassPathResource(rootStaticPath).getFile();
        return rootDir;
    }
    
}
