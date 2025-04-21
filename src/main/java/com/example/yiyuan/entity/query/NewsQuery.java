package com.example.yiyuan.entity.query;



/**
 *
 * @Description:查询对象 *
 * @author:??
 * @date:2025/03/18
 *
 */
public class NewsQuery extends BaseQuery {
	/**
	 * 新闻id
	 */
	private Integer newId;

	/**
	 * 新闻标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 新闻内容
	 */
	private String content;

	private String contentFuzzy;

	/**
	 * 作者
	 */
	private String author;

	private String authorFuzzy;

	/**
	 * 发布时间
	 */
	private String time;

	private String timeFuzzy;

	/**
	 * 点击量
	 */
	private String click;

	private String clickFuzzy;

	public void setNewId(Integer newId){
		this.newId = newId;
	}

	public Integer getNewId(){
		return this.newId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return this.author;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return this.time;
	}

	public void setClick(String click){
		this.click = click;
	}

	public String getClick(){
		return this.click;
	}

	public void setTitleFuzzy(String titleFuzzy){
		this.titleFuzzy = titleFuzzy;
	}

	public String getTitleFuzzy(){
		return this.titleFuzzy;
	}

	public void setContentFuzzy(String contentFuzzy){
		this.contentFuzzy = contentFuzzy;
	}

	public String getContentFuzzy(){
		return this.contentFuzzy;
	}

	public void setAuthorFuzzy(String authorFuzzy){
		this.authorFuzzy = authorFuzzy;
	}

	public String getAuthorFuzzy(){
		return this.authorFuzzy;
	}

	public void setTimeFuzzy(String timeFuzzy){
		this.timeFuzzy = timeFuzzy;
	}

	public String getTimeFuzzy(){
		return this.timeFuzzy;
	}

	public void setClickFuzzy(String clickFuzzy){
		this.clickFuzzy = clickFuzzy;
	}

	public String getClickFuzzy(){
		return this.clickFuzzy;
	}

}