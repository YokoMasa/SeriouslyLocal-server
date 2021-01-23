package com.seriouslylocal.app.common;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class InstagramCommandProcesser implements ArticleCommandProcesser {

    public static final String COMMAND = "instagram";
    private static final String ENDPOINT = "https://graph.facebook.com/v9.0/instagram_oembed";

    @Value("${fbApiKey}")
    private String apiKey;

    private OkHttpClient client = new OkHttpClient();

    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String process(String command, List<String> args) {
        if (!COMMAND.equals(command) || args == null || args.size() == 0) {
            return null;
        }

        return createImageEmbed(args);
    }

    private String createWholeEmbed(List<String> args) {
        String url = ENDPOINT + "?url=" + args.get(0) + "&access_token=" + apiKey;
        Request request = new Request.Builder().url(url).build();
        
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            return jsonObject.get("html").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String createImageEmbed(List<String> args) {
        String url = ENDPOINT + "?url=" + args.get(0) + "&access_token=" + apiKey + "&maxwidth=700" + "&fields=thumbnail_url,author_name,provider_name,provider_url";
        Request request = new Request.Builder().url(url).build();
        
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            System.out.println(body);
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String thumbnailUrl = jsonObject.get("thumbnail_url").getAsString();
            String authorName = jsonObject.get("author_name").getAsString();
            String providerName = jsonObject.get("provider_name").getAsString();
            String providerUrl = jsonObject.get("provider_url").getAsString();

            return HtmlUtil.createInstagramHtml(thumbnailUrl, authorName, args.get(0), providerName, providerUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
