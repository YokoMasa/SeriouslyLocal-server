package com.seriouslylocal.app.common;

import java.util.List;

public interface ArticleCommandProcesser {
    
    public String process(String command, List<String> args);

}
