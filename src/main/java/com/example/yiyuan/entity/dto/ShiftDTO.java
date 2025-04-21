package com.example.yiyuan.entity.dto;

import lombok.Data;

@Data
public class ShiftDTO {
    private String originalDoctorId;
    private String newDoctorId;
    private String shiftDate;
    private String reason;
    private Integer approved;
}
