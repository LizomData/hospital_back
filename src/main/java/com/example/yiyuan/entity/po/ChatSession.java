package com.example.yiyuan.entity.po;

import java.io.Serializable;


/**
 *
 * @Description:会话信息 *
 * @author:??
 * @date:2025/03/20
 *
 */
public class ChatSession implements Serializable {
	/**
	 * 会话ID
	 */
	private String sessionId;

	/**
	 * 最后接受的消息
	 */
	private String lastMessage;

	/**
	 * 未读信息条数
	 */
	private String noReadCount;

	/**
	 * 最后接受消息时间(毫秒)
	 */
	private Long lastReceiveTime;

	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
	}

	public String getSessionId(){
		return this.sessionId;
	}

	public void setLastMessage(String lastMessage){
		this.lastMessage = lastMessage;
	}

	public String getLastMessage(){
		return this.lastMessage;
	}

	public void setNoReadCount(String noReadCount){
		this.noReadCount = noReadCount;
	}

	public String getNoReadCount(){
		return this.noReadCount;
	}

	public void setLastReceiveTime(Long lastReceiveTime){
		this.lastReceiveTime = lastReceiveTime;
	}

	public Long getLastReceiveTime(){
		return this.lastReceiveTime;
	}

	@Override
	public String toString(){
		return "会话ID:" + (sessionId == null ? "空" : sessionId)+",最后接受的消息:" + (lastMessage == null ? "空" : lastMessage)+",未读信息条数:" + (noReadCount == null ? "空" : noReadCount)+",最后接受消息时间(毫秒):" + (lastReceiveTime == null ? "空" : lastReceiveTime);
	}
}