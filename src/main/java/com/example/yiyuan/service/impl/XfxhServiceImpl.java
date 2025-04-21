package com.example.yiyuan.service.impl;
import com.example.yiyuan.entity.config.XfxhConfig;
import com.example.yiyuan.entity.dto.AIMsgDTO;
import com.example.yiyuan.entity.dto.RequestDto;
import com.example.yiyuan.service.XfxhService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class XfxhServiceImpl implements XfxhService {

    private final XfxhConfig config;
    private final RestTemplate restTemplate;

    public XfxhServiceImpl(XfxhConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public String chat(String userId, String content) {
        // 构造请求体
        RequestDto request = new RequestDto(
                config.getModel(),
                userId,
                Collections.singletonList(AIMsgDTO.createUserMsg(content)),
                config.getTemperature(),
                false,
                config.getMaxTokens()
        );

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + config.getApiPassword());

        HttpEntity<RequestDto> requestEntity = new HttpEntity<>(request, headers);

        // 发送请求
        ResponseEntity<String> response = restTemplate.exchange(
                config.getHostUrl(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getBody();
    }
}
