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
 * @date:2025/04/11
 *
 */
public class Report implements Serializable {
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
	private String patientName;

	/**
	 * 
	 */
	private String doctorId;

	/**
	 * 检查日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date checkDate;

	/**
	 * 病例号
	 */
	private String recordNo;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 解释
	 */
	private String comment;

	/**
	 * 签发日期
	 */
	private String date;

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

	public void setPatientName(String patientName){
		this.patientName = patientName;
	}

	public String getPatientName(){
		return this.patientName;
	}

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	public Date getCheckDate(){
		return this.checkDate;
	}

	public void setRecordNo(String recordNo){
		this.recordNo = recordNo;
	}

	public String getRecordNo(){
		return this.recordNo;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return this.date;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (patientId == null ? "空" : patientId)+",:" + (patientName == null ? "空" : patientName)+",:" + (doctorId == null ? "空" : doctorId)+",检查日期:" + (checkDate == null ? "空" : DateUtils.format(checkDate, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+",病例号:" + (recordNo == null ? "空" : recordNo)+",标题:" + (title == null ? "空" : title)+",解释:" + (comment == null ? "空" : comment)+",签发日期:" + (date == null ? "空" : date);
	}
}