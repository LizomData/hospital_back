package com.example.yiyuan.entity.vo;

import lombok.Data;

@Data
public class SymptomCountVO {
    private String name;
    private Long value;

    public SymptomCountVO(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
