package com.example.yiyuan.entity.po;

import java.io.Serializable;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/04/01
 *
 */
public class Comment implements Serializable {
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
	private String doctorId;

	/**
	 * 
	 */
	private String content;

	/**
	 * 
	 */
	private Integer star;

	/**
	 * 0表示线下评论 1表示线上
	 */
	private Integer type;

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

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setStar(Integer star){
		this.star = star;
	}

	public Integer getStar(){
		return this.star;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (patientId == null ? "空" : patientId)+",:" + (doctorId == null ? "空" : doctorId)+",:" + (content == null ? "空" : content)+",:" + (star == null ? "空" : star)+",0表示线下评论 1表示线上:" + (type == null ? "空" : type);
	}
}