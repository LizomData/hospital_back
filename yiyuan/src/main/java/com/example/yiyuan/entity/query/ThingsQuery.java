package com.example.yiyuan.entity.query;



/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/31
 *
 */
public class ThingsQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	private String idFuzzy;

	/**
	 * 
	 */
	private String doctorId;

	private String doctorIdFuzzy;

	/**
	 * 
	 */
	private String patientId;

	private String patientIdFuzzy;

	/**
	 * 
	 */
	private String thing;

	private String thingFuzzy;

	/**
	 * 
	 */
	private String date;

	private String dateFuzzy;

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

	public void setIdFuzzy(String idFuzzy){
		this.idFuzzy = idFuzzy;
	}

	public String getIdFuzzy(){
		return this.idFuzzy;
	}

	public void setDoctorIdFuzzy(String doctorIdFuzzy){
		this.doctorIdFuzzy = doctorIdFuzzy;
	}

	public String getDoctorIdFuzzy(){
		return this.doctorIdFuzzy;
	}

	public void setPatientIdFuzzy(String patientIdFuzzy){
		this.patientIdFuzzy = patientIdFuzzy;
	}

	public String getPatientIdFuzzy(){
		return this.patientIdFuzzy;
	}

	public void setThingFuzzy(String thingFuzzy){
		this.thingFuzzy = thingFuzzy;
	}

	public String getThingFuzzy(){
		return this.thingFuzzy;
	}

	public void setDateFuzzy(String dateFuzzy){
		this.dateFuzzy = dateFuzzy;
	}

	public String getDateFuzzy(){
		return this.dateFuzzy;
	}

}