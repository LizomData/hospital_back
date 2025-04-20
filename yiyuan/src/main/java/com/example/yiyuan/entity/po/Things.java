package com.example.yiyuan.entity.po;

import java.io.Serializable;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/31
 *
 */
public class Things implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String doctorId;

	/**
	 * 
	 */
	private String patientId;

	/**
	 * 
	 */
	private String thing;

	/**
	 * 
	 */
	private String date;

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

	public void setPatientId(String patientId){
		this.patientId = patientId;
	}

	public String getPatientId(){
		return this.patientId;
	}

	public void setThing(String thing){
		this.thing = thing;
	}

	public String getThing(){
		return this.thing;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return this.date;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (doctorId == null ? "空" : doctorId)+",:" + (patientId == null ? "空" : patientId)+",:" + (thing == null ? "空" : thing)+",:" + (date == null ? "空" : date);
	}
}