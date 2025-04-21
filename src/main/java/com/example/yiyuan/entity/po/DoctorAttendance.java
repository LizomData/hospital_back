package com.example.yiyuan.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/30
 *
 */
public class DoctorAttendance implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String doctorId;

	/**
	 * 正常打卡次数
	 */
	private Integer right;

	/**
	 * 迟到打卡次数
	 */
	private Integer wrong;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date time;

	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setRight(Integer right){
		this.right = right;
	}

	public Integer getRight(){
		return this.right;
	}

	public void setWrong(Integer wrong){
		this.wrong = wrong;
	}

	public Integer getWrong(){
		return this.wrong;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (doctorId == null ? "空" : doctorId)+",正常打卡次数:" + (right == null ? "空" : right)+",迟到打卡次数:" + (wrong == null ? "空" : wrong);
	}
}