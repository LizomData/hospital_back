package com.example.yiyuan.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/04/07
 *
 */
public class EyeDisease implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String recordNo;

	/**
	 * 
	 */
	private String category;

	/**
	 * 
	 */
	private String probability;

	/**
	 * 高风险 低风险
	 */
	@JsonIgnore
	private String status;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private String suggestion;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setRecordNo(String recordNo){
		this.recordNo = recordNo;
	}

	public String getRecordNo(){
		return this.recordNo;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return this.category;
	}

	public void setProbability(String probability){
		this.probability = probability;
	}

	public String getProbability(){
		return this.probability;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setSuggestion(String suggestion){
		this.suggestion = suggestion;
	}

	public String getSuggestion(){
		return this.suggestion;
	}

	@Override
	public String toString(){
		return ":" + (id == null ? "空" : id)+",:" + (recordNo == null ? "空" : recordNo)+",:" + (category == null ? "空" : category)+",:" + (probability == null ? "空" : probability)+",高风险 低风险:" + (status == null ? "空" : status)+",:" + (description == null ? "空" : description)+",:" + (suggestion == null ? "空" : suggestion);
	}
}