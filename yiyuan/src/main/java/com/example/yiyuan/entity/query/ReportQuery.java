package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/04/11
 *
 */
public class ReportQuery extends BaseQuery {
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
	private String patientName;

	private String patientNameFuzzy;

	/**
	 * 
	 */
	private String doctorId;

	private String doctorIdFuzzy;

	/**
	 * 检查日期
	 */
	private Date checkDate;

	private String checkDateStart;

	private String checkDateEnd;

	/**
	 * 病例号
	 */
	private String recordNo;

	private String recordNoFuzzy;

	/**
	 * 标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 解释
	 */
	private String comment;

	private String commentFuzzy;

	/**
	 * 签发日期
	 */
	private String date;

	private String dateFuzzy;

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

	public void setPatientIdFuzzy(String patientIdFuzzy){
		this.patientIdFuzzy = patientIdFuzzy;
	}

	public String getPatientIdFuzzy(){
		return this.patientIdFuzzy;
	}

	public void setPatientNameFuzzy(String patientNameFuzzy){
		this.patientNameFuzzy = patientNameFuzzy;
	}

	public String getPatientNameFuzzy(){
		return this.patientNameFuzzy;
	}

	public void setDoctorIdFuzzy(String doctorIdFuzzy){
		this.doctorIdFuzzy = doctorIdFuzzy;
	}

	public String getDoctorIdFuzzy(){
		return this.doctorIdFuzzy;
	}

	public void setCheckDateStart(String checkDateStart){
		this.checkDateStart = checkDateStart;
	}

	public String getCheckDateStart(){
		return this.checkDateStart;
	}

	public void setCheckDateEnd(String checkDateEnd){
		this.checkDateEnd = checkDateEnd;
	}

	public String getCheckDateEnd(){
		return this.checkDateEnd;
	}

	public void setRecordNoFuzzy(String recordNoFuzzy){
		this.recordNoFuzzy = recordNoFuzzy;
	}

	public String getRecordNoFuzzy(){
		return this.recordNoFuzzy;
	}

	public void setTitleFuzzy(String titleFuzzy){
		this.titleFuzzy = titleFuzzy;
	}

	public String getTitleFuzzy(){
		return this.titleFuzzy;
	}

	public void setCommentFuzzy(String commentFuzzy){
		this.commentFuzzy = commentFuzzy;
	}

	public String getCommentFuzzy(){
		return this.commentFuzzy;
	}

	public void setDateFuzzy(String dateFuzzy){
		this.dateFuzzy = dateFuzzy;
	}

	public String getDateFuzzy(){
		return this.dateFuzzy;
	}

}