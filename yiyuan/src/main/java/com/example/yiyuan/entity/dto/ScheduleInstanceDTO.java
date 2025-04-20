package com.example.yiyuan.entity.dto;

import lombok.Data;

@Data
public class ScheduleInstanceDTO {
    private Integer id;
    private String repeatDay;
    private String startTime;
    private String endTime;
}
