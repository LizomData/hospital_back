package com.example.yiyuan.entity.po;

import java.io.Serializable;


/**
 *
 * @Description: *
 * @author:??
 * @date:2025/03/18
 *
 */
public class News implements Serializable {
	/**
	 * 新闻id
	 */
	private Integer newId;

	/**
	 * 新闻标题
	 */
	private String title;

	/**
	 * 新闻内容
	 */
	private String content;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 发布时间
	 */
	private String time;

	/**
	 * 点击量
	 */
	private String click;

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

	@Override
	public String toString(){
		return "新闻id:" + (newId == null ? "空" : newId)+",新闻标题:" + (title == null ? "空" : title)+",新闻内容:" + (content == null ? "空" : content)+",作者:" + (author == null ? "空" : author)+",发布时间:" + (time == null ? "空" : time)+",点击量:" + (click == null ? "空" : click);
	}
}