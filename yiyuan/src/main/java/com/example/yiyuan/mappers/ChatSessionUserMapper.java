package com.example.yiyuan.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @Description:会话用户Mapper *
 * @author:??
 * @date:2025/03/06
 *
 */
public interface ChatSessionUserMapper<T, P> extends BaseMapper {
	/**
	 * 根据 UserIdAndContactId查询
	 */
	T selectByUserIdAndContactId(@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据 UserIdAndContactId更新
	 */
	Integer updateByUserIdAndContactId(@Param("bean") T t ,@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据 UserIdAndContactId删除
	 */
	Integer deleteByUserIdAndContactId(@Param("userId") String userId, @Param("contactId") String contactId);

	Integer updateByParam(T updateInfo, P applyQuery);
	@Update("UPDATE chat_session_user SET contact_name = #{newName} WHERE contact_name = #{oldName}")
	void updateByName(String oldName, String newName);
}