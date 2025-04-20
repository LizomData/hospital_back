package com.example.yiyuan.entity.vo;

import lombok.Data;

@Data
public class ApplyOnlineVo {
    private Integer id;
    private String patient;
    private Integer age;
    private String gender;
    private String type;
    private String symptoms;
    private String status;
    private String submitTime;
    private Integer files;
    private Details details;
}
