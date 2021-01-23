package com.seriouslylocal.app.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MapCommandProcesser implements ArticleCommandProcesser {

    public static final String COMMAND = "map";

    @Value("${mapApiKey}")
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String process(String command, List<String> args) {
        if (!COMMAND.equals(command) || args == null || args.size() == 0) {
            return null;
        }

        String q = args.get(0);
        return HtmlUtil.createMapHtml(apiKey, q);
    }
    
}
