package com.example.yiyuan.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xfxh")
@Data
public class XfxhConfig {
    private String hostUrl;
    private String model;
    private Float temperature;
    private Integer maxTokens;
    private String apiPassword;
}
