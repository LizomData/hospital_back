package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/27
 *
 */
public class ScheduleinstancesQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 排班计划ID
	 */
	private Integer scheduleId;

	/**
	 * 排班日期
	 */
	private Date date;

	private String dateStart;

	private String dateEnd;

	/**
	 * 
	 */
	private String repeatDay;

	private String repeatDayFuzzy;

	/**
	 * 排班开始时间
	 */
	private String startTime;

	private String startTimeFuzzy;

	/**
	 * 排班结束时间
	 */
	private String endTime;

	private String endTimeFuzzy;

	/**
	 * 排班医生ID
	 */
	private String doctorId;

	private String doctorIdFuzzy;

	/**
	 * 排班实例创建时间
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

	public void setScheduleId(Integer scheduleId){
		this.scheduleId = scheduleId;
	}

	public Integer getScheduleId(){
		return this.scheduleId;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}

	public void setRepeatDay(String repeatDay){
		this.repeatDay = repeatDay;
	}

	public String getRepeatDay(){
		return this.repeatDay;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return this.startTime;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return this.endTime;
	}

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}

	public Date getCreatedAt(){
		return this.createdAt;
	}

	public void setDateStart(String dateStart){
		this.dateStart = dateStart;
	}

	public String getDateStart(){
		return this.dateStart;
	}

	public void setDateEnd(String dateEnd){
		this.dateEnd = dateEnd;
	}

	public String getDateEnd(){
		return this.dateEnd;
	}

	public void setRepeatDayFuzzy(String repeatDayFuzzy){
		this.repeatDayFuzzy = repeatDayFuzzy;
	}

	public String getRepeatDayFuzzy(){
		return this.repeatDayFuzzy;
	}

	public void setStartTimeFuzzy(String startTimeFuzzy){
		this.startTimeFuzzy = startTimeFuzzy;
	}

	public String getStartTimeFuzzy(){
		return this.startTimeFuzzy;
	}

	public void setEndTimeFuzzy(String endTimeFuzzy){
		this.endTimeFuzzy = endTimeFuzzy;
	}

	public String getEndTimeFuzzy(){
		return this.endTimeFuzzy;
	}

	public void setDoctorIdFuzzy(String doctorIdFuzzy){
		this.doctorIdFuzzy = doctorIdFuzzy;
	}

	public String getDoctorIdFuzzy(){
		return this.doctorIdFuzzy;
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