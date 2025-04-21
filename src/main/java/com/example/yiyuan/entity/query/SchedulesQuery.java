package com.example.yiyuan.entity.query;

import java.util.Date;


/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/27
 *
 */
public class SchedulesQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 排班计划标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 排班计划描述
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 排班重复类型
	 */
	private String repeatType;

	private String repeatTypeFuzzy;

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
	private Date createdAt;

	private String createdAtStart;

	private String createdAtEnd;

	/**
	 * 背景颜色
	 */
	private String backgroundColor;

	private String backgroundColorFuzzy;

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

	public String getRepeatStartDate() {
		return repeatStartDate;
	}

	public void setRepeatStartDate(String repeatStartDate) {
		this.repeatStartDate = repeatStartDate;
	}

	public String getRepeatEndDate() {
		return repeatEndDate;
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

	public void setTitleFuzzy(String titleFuzzy){
		this.titleFuzzy = titleFuzzy;
	}

	public String getTitleFuzzy(){
		return this.titleFuzzy;
	}

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
	}

	public void setRepeatTypeFuzzy(String repeatTypeFuzzy){
		this.repeatTypeFuzzy = repeatTypeFuzzy;
	}

	public String getRepeatTypeFuzzy(){
		return this.repeatTypeFuzzy;
	}

	public void setCreatedAtStart(String createdAtStart){
		this.createdAtStart = createdAtStart;
	}

	public String getCreatedAtStart(){
		return this.createdAtStart;
	}

	public void setCreatedAtEnd(String createdAtEnd){
		this.createdAtEnd = createdAtEnd;
	}

	public String getCreatedAtEnd(){
		return this.createdAtEnd;
	}

	public void setBackgroundColorFuzzy(String backgroundColorFuzzy){
		this.backgroundColorFuzzy = backgroundColorFuzzy;
	}

	public String getBackgroundColorFuzzy(){
		return this.backgroundColorFuzzy;
	}

}