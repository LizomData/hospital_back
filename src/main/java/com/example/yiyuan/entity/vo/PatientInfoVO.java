package com.example.yiyuan.entity.vo;

import lombok.Data;

@Data
public class PatientInfoVO {
    private String name;
    private Integer age;
    private String gender;
    private String diagnosis;
    private String history;
    private String lastCheckup;
}
