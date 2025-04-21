package com.example.yiyuan.entity.query;



/**
 *
 * @Description:会话信息查询对象 *
 * @author:??
 * @date:2025/03/20
 *
 */
public class ChatSessionQuery extends BaseQuery {
	/**
	 * 会话ID
	 */
	private String sessionId;

	private String sessionIdFuzzy;

	/**
	 * 最后接受的消息
	 */
	private String lastMessage;

	private String lastMessageFuzzy;

	/**
	 * 未读信息条数
	 */
	private String noReadCount;

	private String noReadCountFuzzy;

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

	public void setSessionIdFuzzy(String sessionIdFuzzy){
		this.sessionIdFuzzy = sessionIdFuzzy;
	}

	public String getSessionIdFuzzy(){
		return this.sessionIdFuzzy;
	}

	public void setLastMessageFuzzy(String lastMessageFuzzy){
		this.lastMessageFuzzy = lastMessageFuzzy;
	}

	public String getLastMessageFuzzy(){
		return this.lastMessageFuzzy;
	}

	public void setNoReadCountFuzzy(String noReadCountFuzzy){
		this.noReadCountFuzzy = noReadCountFuzzy;
	}

	public String getNoReadCountFuzzy(){
		return this.noReadCountFuzzy;
	}

}