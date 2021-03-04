package com.seriouslylocal.app.common;

import com.seriouslylocal.app.entity.ImageInfo;

import org.springframework.util.StringUtils;

public class HtmlUtil {
    
    public static String createCreditHtml(ImageInfo imageInfo) {
        if (imageInfo == null) {
            return "";
        }

        if (StringUtils.hasText(imageInfo.getCreditDisplay()) && StringUtils.hasText(imageInfo.getCreditUrl())) {
            return "<a href=\"" + imageInfo.getCreditUrl() + "\" target=\"_blank\" class=\"credit\">" + imageInfo.getCreditDisplay() + "</a>";
        } else if (StringUtils.hasText(imageInfo.getCreditDisplay())) {
            return "<span style=\"color: #bbb; font-size: 10pt;\">" + imageInfo.getCreditDisplay() + "</span>";
        } else if (StringUtils.hasText(imageInfo.getCreditUrl())) {
            return "<a href=\"" + imageInfo.getCreditUrl() + "\" target=\"_blank\" class=\"credit\">" + imageInfo.getCreditUrl() + "</a>";
        }
        return "";
    }

    public static String createMapHtml(String apiKey, String q) {
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(q)){
            return "";
        }

        return "<iframe loading=\"lazy\" width=\"100%\" height=\"400px\" frameborder=\"0\" style=\"border: 0;\" src=\"https://www.google.com/maps/embed/v1/place?key=" + apiKey +
                    "&q=" + q + "\" allowfullscreen></iframe>";
    }

    public static String createInstagramHtml(String url, String authorName, String authorUrl, String providerName, String providerUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("<img src=\"").append(url).append("\"/><br>")
                .append("<span style=\"color: #bbb; font-size: 10pt;\">")
                .append("<a href=\"").append(authorUrl).append("\">").append(authorName).append("</a>,&nbsp;")
                .append("<a href=\"").append(providerUrl).append("\">").append(providerName).append("</a>");
        return sb.toString();
    }

}
