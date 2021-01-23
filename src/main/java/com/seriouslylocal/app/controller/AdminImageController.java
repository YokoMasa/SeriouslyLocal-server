package com.seriouslylocal.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.entity.ImageInfo;
import com.seriouslylocal.app.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/api/image")
public class AdminImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping(produces = {"application/json"})
    public List<ImageInfo> listImage(@RequestParam(name = "page", defaultValue = "1") int page) {
        return imageService.list(page);
    }

    @PostMapping
    public ResponseEntity createImage(@RequestParam("image") MultipartFile image, @RequestParam("thumbnail") MultipartFile thumbnail,
                                            @RequestParam("credit_display") Optional<String> creditDisplay, @RequestParam("credit_url") Optional<String> creditUrl) {
        String fileName = createFileName(image);
        if (fileName == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            imageService.save(image.getInputStream(), thumbnail.getInputStream(), fileName, creditDisplay.orElse(""), creditUrl.orElse(""));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String createFileName(MultipartFile image) {
        String fileName = String.valueOf(System.currentTimeMillis());
        switch (image.getContentType()) {
            case "image/jpeg":
            return fileName + ".jpg";
            case "image/png":
            return fileName + ".png";
            case "image/gif":
            return fileName + ".gif";
            default:
            return null;
        }
    }

}
