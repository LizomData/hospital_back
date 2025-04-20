package com.example.yiyuan.mappers;

import org.apache.ibatis.annotations.Param;
/**
 *
 * @Description:会话信息Mapper *
 * @author:??
 * @date:2025/03/06
 *
 */
public interface ChatSessionMapper<T, P> extends BaseMapper {
	/**
	 * 根据 SessionId查询
	 */
	T selectBySessionId(@Param("sessionId") String sessionId);

	/**
	 * 根据 SessionId更新
	 */
	Integer updateBySessionId(@Param("bean") T t ,@Param("sessionId") String sessionId);

	/**
	 * 根据 SessionId删除
	 */
	Integer deleteBySessionId(@Param("sessionId") String sessionId);


}