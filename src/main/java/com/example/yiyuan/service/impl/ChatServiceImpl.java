package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.constants.Constants;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service("chatServiceImpl")
public class ChatServiceImpl {
    private static final String API_URL = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";
    private static final String API_KEY = "Bearer 37944408-56ea-4717-b270-a84df112228d";
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间为 30 秒
            .readTimeout(60, TimeUnit.SECONDS)   // 设置读取超时时间为 60 秒
            .writeTimeout(60, TimeUnit.SECONDS)  // 设置写入超时时间为 60 秒
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String chat(String content) {
        Response response = null;
        try {
            String jsonBody = "{\n" +
                    "  \"model\": \"deepseek-v3-250324\",\n" +
                    "  \"messages\": [\n" +
                    "    {\"role\": \"user\", \"content\": \"" + content + "\"}\n" +
                    "  ]\n" +
                    "}";

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", API_KEY)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed: " + response);
            }
            JsonNode rootNode = objectMapper.readTree(response.body().string());
            return rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }

    public static String chatPhoto(String text, String imageUrl) {
        Response response = null;
        try {
            String jsonBody = "{\"model\": \"doubao-1-5-vision-pro-32k-250115\", \"messages\": [{\"role\": \"user\", \"content\": [" +
                    "{\"type\": \"text\", \"text\": \"" + text + "\"}," +
                    "{\"type\": \"image_url\", \"image_url\": {\"url\": \"" + imageUrl + "\"}}" +
                    "]}]}\n";

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", API_KEY)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed: " + response);
            }

            JsonNode rootNode = objectMapper.readTree(response.body().string());
            return rootNode.path("choices").get(0).path("message").path("content").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
    }
}
