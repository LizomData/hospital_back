package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/26
 */
public class UserOutlineApplyQuery extends BaseQuery {
    /**
     * 自增ID
     */
    private Integer applyId;

    /**
     * 申请人ID
     */
    private String applyUserId;

    private String applyUserIdFuzzy;

    /**
     * 接收人ID
     */
    private String receiveUserId;

    private String receiveUserIdFuzzy;

    /**
     * 最后申请时间
     */
    private Date lastApplyTime;

    private String lastApplyTimeStart;

    private String lastApplyTimeEnd;

    /**
     * 状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑 4:咨询结束
     */
    private Integer status;

    /**
     * 症状
     */
    private String symptoms;

    private String symptomsFuzzy;

    /**
     * 持续时间
     */
    private String duration;

    private String durationFuzzy;

    /**
     * 左眼病情
     */
    private String leftEye;

    private String leftEyeFuzzy;

    /**
     * 右眼病情
     */
    private String rightEye;

    private String rightEyeFuzzy;
    private Integer userStatus;

    /**
     * 严重程度
     */
    private Integer painLevel;

    private String nickName;


    /**
     * 附件数量
     */
    private String file;

    private String fileFuzzy;
    /**
     * 预约的时间段
     */
    private String submitTime;

    private String submitTimeFuzzy;

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitTimeFuzzy() {
        return submitTimeFuzzy;
    }

    public void setSubmitTimeFuzzy(String submitTimeFuzzy) {
        this.submitTimeFuzzy = submitTimeFuzzy;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyId() {
        return this.applyId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserId() {
        return this.applyUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserId() {
        return this.receiveUserId;
    }

    public void setLastApplyTime(Date lastApplyTime) {
        this.lastApplyTime = lastApplyTime;
    }

    public Date getLastApplyTime() {
        return this.lastApplyTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptoms() {
        return this.symptoms;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setLeftEye(String leftEye) {
        this.leftEye = leftEye;
    }

    public String getLeftEye() {
        return this.leftEye;
    }

    public void setRightEye(String rightEye) {
        this.rightEye = rightEye;
    }

    public String getRightEye() {
        return this.rightEye;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Integer getPainLevel() {
        return this.painLevel;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return this.file;
    }

    public void setApplyUserIdFuzzy(String applyUserIdFuzzy) {
        this.applyUserIdFuzzy = applyUserIdFuzzy;
    }

    public String getApplyUserIdFuzzy() {
        return this.applyUserIdFuzzy;
    }

    public void setReceiveUserIdFuzzy(String receiveUserIdFuzzy) {
        this.receiveUserIdFuzzy = receiveUserIdFuzzy;
    }

    public String getReceiveUserIdFuzzy() {
        return this.receiveUserIdFuzzy;
    }

    public void setLastApplyTimeStart(String lastApplyTimeStart) {
        this.lastApplyTimeStart = lastApplyTimeStart;
    }

    public String getLastApplyTimeStart() {
        return this.lastApplyTimeStart;
    }

    public void setLastApplyTimeEnd(String lastApplyTimeEnd) {
        this.lastApplyTimeEnd = lastApplyTimeEnd;
    }

    public String getLastApplyTimeEnd() {
        return this.lastApplyTimeEnd;
    }

    public void setSymptomsFuzzy(String symptomsFuzzy) {
        this.symptomsFuzzy = symptomsFuzzy;
    }

    public String getSymptomsFuzzy() {
        return this.symptomsFuzzy;
    }

    public void setDurationFuzzy(String durationFuzzy) {
        this.durationFuzzy = durationFuzzy;
    }

    public String getDurationFuzzy() {
        return this.durationFuzzy;
    }

    public void setLeftEyeFuzzy(String leftEyeFuzzy) {
        this.leftEyeFuzzy = leftEyeFuzzy;
    }

    public String getLeftEyeFuzzy() {
        return this.leftEyeFuzzy;
    }

    public void setRightEyeFuzzy(String rightEyeFuzzy) {
        this.rightEyeFuzzy = rightEyeFuzzy;
    }

    public String getRightEyeFuzzy() {
        return this.rightEyeFuzzy;
    }

    public void setFileFuzzy(String fileFuzzy) {
        this.fileFuzzy = fileFuzzy;
    }

    public String getFileFuzzy() {
        return this.fileFuzzy;
    }

    public String getNickName() {
        return nickName;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}