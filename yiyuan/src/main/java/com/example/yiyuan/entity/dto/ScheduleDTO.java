package com.example.yiyuan.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleDTO {
    private int id;
    private String title;
    private String description;
    private String repeatType;
    private String repeatStartDate;
    private String repeatEndDate;
    private String backgroundColor;
    private List<ScheduleInstanceDTO> instances;
    private List<ShiftDTO> shifts;

}