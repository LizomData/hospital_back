package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.po.ChatMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *
 * @Description:聊天消息表Mapper *
 * @author:??
 * @date:2025/03/06
 *
 */
public interface ChatMessageMapper<T, P> extends BaseMapper {
	/**
	 * 根据 MessageId查询
	 */
	T selectByMessageId(@Param("messageId") Long messageId);

	/**
	 * 根据 MessageId更新
	 */
	Integer updateByMessageId(@Param("bean") T t ,@Param("messageId") Long messageId);

	/**
	 * 根据 MessageId删除
	 */
	Integer deleteByMessageId(@Param("messageId") Long messageId);

	Integer updateByParam(T updateInfo, P applyQuery);
	@Update("UPDATE chat_message SET send_user_nick_name = #{newName} WHERE send_user_nick_name = #{oldName}")
	void updateChatMessageByName(String oldName, String newName);

	@Select("SELECT DISTINCT * " +
			"FROM chat_message " +
			"WHERE session_id = #{sessionId} " +
			"AND EXISTS (SELECT 1 FROM chat_session_user WHERE chat_session_user.session_id = chat_message.session_id AND chat_session_user.user_id = #{userId})")
	List<ChatMessage> getMessageList(String sessionId, String userId);

}