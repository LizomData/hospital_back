package com.example.yiyuan.entity.enums;


import com.example.yiyuan.utils.StringTools;

public enum JoinTypeEnum {
    JOIN(0,"直接加入"),
    APPLY(1,"需要审核");

    private Integer type;
    private String desc;

    JoinTypeEnum(Integer type,String desc){
        this.desc=desc;
        this.type=type;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static JoinTypeEnum getByName(String name){
        try{
            if (StringTools.isEmpty(name)){
                return null;
            }
            return JoinTypeEnum.valueOf(name.toUpperCase());
        }catch (IllegalArgumentException e){
            return null;
        }
    }

    public static JoinTypeEnum getByType(Integer joinType){
        for (JoinTypeEnum joinTypeEnum:JoinTypeEnum.values()){
            if (joinTypeEnum.getType().equals(joinType)){
                return joinTypeEnum;
            }
        }
        return null;
    }
}
