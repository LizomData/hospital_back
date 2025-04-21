package com.example.yiyuan.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class XuetangVO {
    private List<Object[]> data;

    public XuetangVO(List<Object[]> data) {
        this.data = data;
    }
}
