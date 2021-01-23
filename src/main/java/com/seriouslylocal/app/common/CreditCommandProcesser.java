package com.seriouslylocal.app.common;

import java.util.List;
import java.util.Optional;

import com.seriouslylocal.app.entity.ImageInfo;
import com.seriouslylocal.app.repository.ImageInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditCommandProcesser implements ArticleCommandProcesser {

    public static final String COMMAND = "credit";

    private ImageInfoRepository repository;

    @Autowired
    public CreditCommandProcesser(ImageInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public String process(String command, List<String> args) {
        if (!COMMAND.equals(command) || args == null || args.size() == 0) {
            return null;
        }

        int imageId = 0;
        try {
            imageId = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            System.out.println("invalid image id: " + args.get(0));
            return "";
        }

        Optional<ImageInfo> optional = repository.findById(imageId);
        if (optional.isPresent()) {
            return HtmlUtil.createCreditHtml(optional.get());
        } else {
            return "";
        }
    }
    
}
