package com.example.yiyuan.entity.vo;

import lombok.Data;

@Data
public class OutlineApplyVO {


    private Integer id;
    private String patientId;
    private String patient;
    private Integer age;
    private String gender;
    private String symptoms;
    private String status;
    private String submitTime;
    private Integer files;
    private Details details;


}
