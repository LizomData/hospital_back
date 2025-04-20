package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/04/01
 *
 */
public class DiseaseQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String patientId;

	private String patientIdFuzzy;

	/**
	 * 
	 */
	private String diseaseName;

	private String diseaseNameFuzzy;

	/**
	 * 
	 */
	private Date time;

	private String timeStart;

	private String timeEnd;

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

	public void setPatientIdFuzzy(String patientIdFuzzy){
		this.patientIdFuzzy = patientIdFuzzy;
	}

	public String getPatientIdFuzzy(){
		return this.patientIdFuzzy;
	}

	public void setDiseaseNameFuzzy(String diseaseNameFuzzy){
		this.diseaseNameFuzzy = diseaseNameFuzzy;
	}

	public String getDiseaseNameFuzzy(){
		return this.diseaseNameFuzzy;
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