package com.example.yiyuan.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/24
 *
 */
public class UserContactApply implements Serializable {
    /**
     * 自增ID
     */
    private Integer applyId;

    /**
     * 申请人ID
     */
    private String applyUserId;

    /**
     * 接收人ID
     */
    private String receiveUserId;

    /**
     * 最后申请时间
     */
    private Long lastApplyTime;

    /**
     * 状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑 4:咨询结束
     */
    @JsonIgnore
    private Integer status;

    /**
     * 申请信息
     */
    private String applyInfo;

    /**
     * 申请次数
     */
    private String number;

    /**
     * 咨询类型
     */
    private String type;

    /**
     * 持续时间
     */
    private String duration;

    /**
     * 左眼病情
     */
    private String leftEye;

    /**
     * 右眼病情
     */
    private String rightEye;

    /**
     * 严重程度
     */
    private Integer painLevel;

    /**
     * 附件数量
     */
    private String file;

    public void setApplyId(Integer applyId){
        this.applyId = applyId;
    }

    public Integer getApplyId(){
        return this.applyId;
    }

    public void setApplyUserId(String applyUserId){
        this.applyUserId = applyUserId;
    }

    public String getApplyUserId(){
        return this.applyUserId;
    }

    public void setReceiveUserId(String receiveUserId){
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserId(){
        return this.receiveUserId;
    }

    public void setLastApplyTime(Long lastApplyTime){
        this.lastApplyTime = lastApplyTime;
    }

    public Long getLastApplyTime(){
        return this.lastApplyTime;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }

    public void setApplyInfo(String applyInfo){
        this.applyInfo = applyInfo;
    }

    public String getApplyInfo(){
        return this.applyInfo;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getNumber(){
        return this.number;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getDuration(){
        return this.duration;
    }

    public void setLeftEye(String leftEye){
        this.leftEye = leftEye;
    }

    public String getLeftEye(){
        return this.leftEye;
    }

    public void setRightEye(String rightEye){
        this.rightEye = rightEye;
    }

    public String getRightEye(){
        return this.rightEye;
    }

    public void setPainLevel(Integer painLevel){
        this.painLevel = painLevel;
    }

    public Integer getPainLevel(){
        return this.painLevel;
    }

    public void setFile(String file){
        this.file = file;
    }

    public String getFile(){
        return this.file;
    }

    @Override
    public String toString(){
        return "自增ID:" + (applyId == null ? "空" : applyId)+",申请人ID:" + (applyUserId == null ? "空" : applyUserId)+",接收人ID:" + (receiveUserId == null ? "空" : receiveUserId)+",最后申请时间:" + (lastApplyTime == null ? "空" : lastApplyTime)+",状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑 4:咨询结束:" + (status == null ? "空" : status)+",申请信息:" + (applyInfo == null ? "空" : applyInfo)+",申请次数:" + (number == null ? "空" : number)+",咨询类型:" + (type == null ? "空" : type)+",持续时间:" + (duration == null ? "空" : duration)+",左眼病情:" + (leftEye == null ? "空" : leftEye)+",右眼病情:" + (rightEye == null ? "空" : rightEye)+",严重程度:" + (painLevel == null ? "空" : painLevel)+",附件数量:" + (file == null ? "空" : file);
    }
}