package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/04/07
 *
 */
public class DoctorAttendanceQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String doctorId;

	private String doctorIdFuzzy;

	/**
	 * 正常打卡次数
	 */
	private Integer right;

	/**
	 * 迟到打卡次数
	 */
	private Integer wrong;

	/**
	 * 
	 */
	private Date time;

	private String timeStart;

	private String timeEnd;

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

	public void setTime(Date time){
		this.time = time;
	}

	public Date getTime(){
		return this.time;
	}

	public void setDoctorIdFuzzy(String doctorIdFuzzy){
		this.doctorIdFuzzy = doctorIdFuzzy;
	}

	public String getDoctorIdFuzzy(){
		return this.doctorIdFuzzy;
	}

	public void setTimeStart(String timeStart){
		this.timeStart = timeStart;
	}

	public String getTimeStart(){
		return this.timeStart;
	}

	public void setTimeEnd(String timeEnd){
		this.timeEnd = timeEnd;
	}

	public String getTimeEnd(){
		return this.timeEnd;
	}

}