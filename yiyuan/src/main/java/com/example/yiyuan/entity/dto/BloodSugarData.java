package com.example.yiyuan.entity.dto;

import lombok.Data;

@Data
public class BloodSugarData {
    private String date;
    private Integer value;

    public BloodSugarData(String date, Integer value) {
        this.date = date;
        this.value = value;
    }
}
