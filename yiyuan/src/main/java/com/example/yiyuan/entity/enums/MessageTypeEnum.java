package com.example.yiyuan.entity.enums;

public enum MessageTypeEnum {
    INIT(0, "", "连接WS获取信息"),
    ADD_FRIEND(1, "", "添加好友打招呼消息"),
    CHAT(2, "", "普通聊天消息"),
    CONTACT_APPLY(3, "", "好友申请"),
    MEDIA_CHAT(4, "", "媒体文件"),
    FILE_UPLOAD(5, "", "文件上传完成"),
    FORCE_OFF_LINE(6, "", "强制下线"),
    ADD_FRIEND_SELF(7, "", "添加好友打招呼消息"),
    CONTACT_NAME_UPDATE(8,"","更新昵称") ;
    private Integer type;
    private String initMessage;
    private String desc;

    MessageTypeEnum(Integer type, String initMessage, String desc) {
        this.type = type;
        this.initMessage = initMessage;
        this.desc = desc;
    }

    public static MessageTypeEnum getByType(Integer type) {
        for (MessageTypeEnum item : MessageTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getInitMessage() {
        return initMessage;
    }

    public String getDesc() {
        return desc;
    }
}
