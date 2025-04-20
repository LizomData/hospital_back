package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/27
 *
 */
public class ShiftsQuery extends BaseQuery {
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

	private String originalDoctorIdFuzzy;

	/**
	 * 替换医生ID
	 */
	private String newDoctorId;

	private String newDoctorIdFuzzy;

	/**
	 * 换班原因
	 */
	private String reason;

	private String reasonFuzzy;

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
	private Date createdAt;

	private String createdAtStart;

	private String createdAtEnd;

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

	public void setOriginalDoctorIdFuzzy(String originalDoctorIdFuzzy){
		this.originalDoctorIdFuzzy = originalDoctorIdFuzzy;
	}

	public String getOriginalDoctorIdFuzzy(){
		return this.originalDoctorIdFuzzy;
	}

	public void setNewDoctorIdFuzzy(String newDoctorIdFuzzy){
		this.newDoctorIdFuzzy = newDoctorIdFuzzy;
	}

	public String getNewDoctorIdFuzzy(){
		return this.newDoctorIdFuzzy;
	}

	public void setReasonFuzzy(String reasonFuzzy){
		this.reasonFuzzy = reasonFuzzy;
	}

	public String getReasonFuzzy(){
		return this.reasonFuzzy;
	}



	public void setCreatedAtStart(String createdAtStart){
		this.createdAtStart = createdAtStart;
	}

	public String getCreatedAtStart(){
		return this.createdAtStart;
	}

	public void setCreatedAtEnd(String createdAtEnd){
		this.createdAtEnd = createdAtEnd;
	}

	public String getCreatedAtEnd(){
		return this.createdAtEnd;
	}

}