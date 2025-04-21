package com.example.yiyuan.entity.dto;

import lombok.Data;

@Data
public class BloodYaDto {
    private String date;
    private double value;

    public BloodYaDto(String date, double value) {
        this.date = date;
        this.value = value;
    }
}
