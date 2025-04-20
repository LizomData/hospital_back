package com.example.yiyuan.entity.query;



/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/04/07
 *
 */
public class EyeDiseaseQuery extends BaseQuery {
	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String recordNo;

	private String recordNoFuzzy;

	/**
	 * 
	 */
	private String category;

	private String categoryFuzzy;

	/**
	 * 
	 */
	private String probability;

	private String probabilityFuzzy;

	/**
	 * 高风险 低风险
	 */
	private String status;

	private String statusFuzzy;

	/**
	 * 
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 
	 */
	private String suggestion;

	private String suggestionFuzzy;

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

	public void setRecordNoFuzzy(String recordNoFuzzy){
		this.recordNoFuzzy = recordNoFuzzy;
	}

	public String getRecordNoFuzzy(){
		return this.recordNoFuzzy;
	}

	public void setCategoryFuzzy(String categoryFuzzy){
		this.categoryFuzzy = categoryFuzzy;
	}

	public String getCategoryFuzzy(){
		return this.categoryFuzzy;
	}

	public void setProbabilityFuzzy(String probabilityFuzzy){
		this.probabilityFuzzy = probabilityFuzzy;
	}

	public String getProbabilityFuzzy(){
		return this.probabilityFuzzy;
	}

	public void setStatusFuzzy(String statusFuzzy){
		this.statusFuzzy = statusFuzzy;
	}

	public String getStatusFuzzy(){
		return this.statusFuzzy;
	}

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
	}

	public void setSuggestionFuzzy(String suggestionFuzzy){
		this.suggestionFuzzy = suggestionFuzzy;
	}

	public String getSuggestionFuzzy(){
		return this.suggestionFuzzy;
	}

}