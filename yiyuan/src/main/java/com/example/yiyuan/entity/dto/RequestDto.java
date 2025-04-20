package com.example.yiyuan.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RequestDto {
    private String model;
    private String user;
    private List<AIMsgDTO> messages;
    private Float temperature;
    private Boolean stream;

    @JSONField(name = "max_tokens")
    @JsonProperty("max_tokens")
    private Integer maxTokens;
}
