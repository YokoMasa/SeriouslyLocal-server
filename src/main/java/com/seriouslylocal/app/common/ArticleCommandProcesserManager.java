package com.seriouslylocal.app.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArticleCommandProcesserManager {

    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\[(\\w+:\\s*[^\\]]+)\\]");

    private List<ArticleCommandProcesser> processers = new ArrayList<>();

    public void addProcesser(ArticleCommandProcesser processer) {
        processers.add(processer);
    }
    
    public String process(String s) {
        List<String> processedList = new ArrayList<>();
        int current = 0;

        Matcher matcher = COMMAND_PATTERN.matcher(s);
        while (matcher.find()) {
            String wholeCommand = matcher.group(1);
            System.out.println("whole command: " + wholeCommand);

            String[] splitted = wholeCommand.split(":", 2);
            String command = splitted[0];
            List<String> args = Arrays.stream(splitted[1].split(",")).map(String::trim).collect(Collectors.toList());

            String processed = processCommand(command, args);
            if (processed != null) {
                processedList.add(s.substring(current, matcher.start()));
                processedList.add(processed);
                current = matcher.end();
            }
        }

        processedList.add(s.substring(current));
        return String.join("", processedList);
    }

    private String processCommand(String command, List<String> args) {
        System.out.println("■command: " + command);
        System.out.print("■args: ");
        args.stream().forEach(arg -> System.out.print(arg + ", "));
        System.out.println("");

        for (ArticleCommandProcesser processer: processers) {
            String processed = processer.process(command, args);
            if (processed != null) {
                return processed;
            }
        }
        return null;
    }

}
