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
public class Scheduleinstances implements Serializable {
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
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	/**
	 * 
	 */
	private String repeatDay;

	/**
	 * 排班开始时间
	 */
	private String startTime;

	/**
	 * 排班结束时间
	 */
	private String endTime;

	/**
	 * 排班医生ID
	 */
	private String doctorId;

	/**
	 * 排班实例创建时间
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

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",排班计划ID:" + (scheduleId == null ? "空" : scheduleId)+",排班日期:" + (date == null ? "空" : DateUtils.format(date, DateTimePatternEnum.YYYY_MM_DD.getPattern()))+",:" + (repeatDay == null ? "空" : repeatDay)+",排班开始时间:" + (startTime == null ? "空" : startTime)+",排班结束时间:" + (endTime == null ? "空" : endTime)+",排班医生ID:" + (doctorId == null ? "空" : doctorId)+",排班实例创建时间:" + (createdAt == null ? "空" : DateUtils.format(createdAt,DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}