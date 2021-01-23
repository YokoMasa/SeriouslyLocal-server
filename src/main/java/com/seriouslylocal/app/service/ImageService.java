package com.seriouslylocal.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.seriouslylocal.app.entity.ImageInfo;

public interface ImageService {
    public ImageInfo save(InputStream original, InputStream thumbnail, String fileName, String creditDisplay, String creditUrl) throws IOException;

    public List<ImageInfo> list(int page);
}
