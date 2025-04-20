package com.example.yiyuan.entity.po;

import com.example.yiyuan.entity.enums.DateTimePatternEnum;
import com.example.yiyuan.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/27
 *
 */
public class Shifts implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 排班实例ID
	 */
	private Integer scheduleInstanceId;

	/**
	 * 原排班医生ID
	 */
	private String originalDoctorId;

	/**
	 * 替换医生ID
	 */
	private String newDoctorId;

	/**
	 * 换班原因
	 */
	private String reason;

	/**
	 * 是否已批准
	 */
	private Integer approved;

	/**
	 * 换班日期
	 */
	private String shiftDate;

	/**
	 * 换班记录创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setScheduleInstanceId(Integer scheduleInstanceId){
		this.scheduleInstanceId = scheduleInstanceId;
	}

	public Integer getScheduleInstanceId(){
		return this.scheduleInstanceId;
	}

	public void setOriginalDoctorId(String originalDoctorId){
		this.originalDoctorId = originalDoctorId;
	}

	public String getOriginalDoctorId(){
		return this.originalDoctorId;
	}

	public void setNewDoctorId(String newDoctorId){
		this.newDoctorId = newDoctorId;
	}

	public String getNewDoctorId(){
		return this.newDoctorId;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return this.reason;
	}

	public void setApproved(Integer approved){
		this.approved = approved;
	}

	public Integer getApproved(){
		return this.approved;
	}

	public String getShiftDate() {
		return shiftDate;
	}

	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}

	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}

	public Date getCreatedAt(){
		return this.createdAt;
	}

}