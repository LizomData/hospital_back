package com.example.yiyuan.entity.po;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.DateTimePatternEnum;
import com.example.yiyuan.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/26
 *
 */
public class UserOutlineApply implements Serializable {
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastApplyTime;

	/**
	 * 状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑 4:咨询结束
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 症状
	 */
	private String symptoms;

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

	/**
	 * 预约的时间段
	 */
	private String submitTime;

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

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

	public void setLastApplyTime(Date lastApplyTime){
		this.lastApplyTime = lastApplyTime;
	}

	public Date getLastApplyTime(){
		return this.lastApplyTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setSymptoms(String symptoms){
		this.symptoms = symptoms;
	}

	public String getSymptoms(){
		return this.symptoms;
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
		return "自增ID:" + (applyId == null ? "空" : applyId)+",申请人ID:" + (applyUserId == null ? "空" : applyUserId)+",接收人ID:" + (receiveUserId == null ? "空" : receiveUserId)+",最后申请时间:" + (lastApplyTime == null ? "空" : DateUtils.format(lastApplyTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+",状态 0:待处理 1:已同意 2:已拒绝 3:已拉黑 4:咨询结束:" + (status == null ? "空" : status)+",症状:" + (symptoms == null ? "空" : symptoms)+",持续时间:" + (duration == null ? "空" : duration)+",左眼病情:" + (leftEye == null ? "空" : leftEye)+",右眼病情:" + (rightEye == null ? "空" : rightEye)+",严重程度:" + (painLevel == null ? "空" : painLevel)+",附件数量:" + (file == null ? "空" : file);
	}

}