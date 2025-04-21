package com.example.yiyuan.entity.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ReportRequestDTO {
    private String patientId;
    private String patientName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;
    private String recordNo;
    private List<EyeDiseaseDTO> eyeDiseases;
    private String doctorId;
    private String date;

    @Data
    public static class EyeDiseaseDTO {
        private String category;
        private Double probability;
        private String status;
        private String description;
        private String suggestion;
    }
}