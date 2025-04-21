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
 * @date:2025/04/01
 *
 */
public class Disease implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String patientId;

	/**
	 * 
	 */
	private String diseaseName;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	/**
	 * 
	 */
	private Integer severity;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setPatientId(String patientId){
		this.patientId = patientId;
	}

	public String getPatientId(){
		return this.patientId;
	}

	public void setDiseaseName(String diseaseName){
		this.diseaseName = diseaseName;
	}

	public String getDiseaseName(){
		return this.diseaseName;
	}

	public void setTime(Date time){
		this.time = time;
	}

	public Date getTime(){
		return this.time;
	}

	public void setSeverity(Integer severity){
		this.severity = severity;
	}

	public Integer getSeverity(){
		return this.severity;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (patientId == null ? "空" : patientId)+",:" + (diseaseName == null ? "空" : diseaseName)+",:" + (time == null ? "空" : DateUtils.format(time, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+",:" + (severity == null ? "空" : severity);
	}
}