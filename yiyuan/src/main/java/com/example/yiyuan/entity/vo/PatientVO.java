package com.example.yiyuan.entity.vo;

import lombok.Data;

@Data
public class PatientVO {
    private String patientId;
    private String name;
    private Integer age;
    private String symptoms;
    private String progress;
    private Integer level;

}
