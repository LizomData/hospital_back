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
 * @date:2025/03/27
 *
 */
public class Schedules implements Serializable {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 排班计划标题
	 */
	private String title;

	/**
	 * 排班计划描述
	 */
	private String description;

	/**
	 * 排班重复类型
	 */
	private String repeatType;

	/**
	 * 排班开始日期
	 */

	private String repeatStartDate;

	/**
	 * 排班结束日期
	 */
	private String repeatEndDate;

	/**
	 * 排班计划创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	/**
	 * 背景颜色
	 */
	private String backgroundColor;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setRepeatType(String repeatType){
		this.repeatType = repeatType;
	}

	public String getRepeatType(){
		return this.repeatType;
	}

	public String getRepeatEndDate() {
		return repeatEndDate;
	}

	public String getRepeatStartDate() {
		return repeatStartDate;
	}

	public void setRepeatStartDate(String repeatStartDate) {
		this.repeatStartDate = repeatStartDate;
	}

	public void setRepeatEndDate(String repeatEndDate) {
		this.repeatEndDate = repeatEndDate;
	}

	public void setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
	}

	public Date getCreatedAt(){
		return this.createdAt;
	}

	public void setBackgroundColor(String backgroundColor){
		this.backgroundColor = backgroundColor;
	}

	public String getBackgroundColor(){
		return this.backgroundColor;
	}

}