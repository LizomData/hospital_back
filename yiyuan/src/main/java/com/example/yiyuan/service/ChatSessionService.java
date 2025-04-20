package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.ChatSession;
import com.example.yiyuan.entity.query.ChatSessionQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description:会话信息 Service *
 * @author:??
 * @date:2025/03/06
 *
 */
public interface ChatSessionService {
	/**
	 * 根据条件查询列表
	 */
	List<ChatSession> findListByParam(ChatSessionQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ChatSessionQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ChatSession> findListByPage(ChatSessionQuery query);

	/**
	 * 新增
	 */
	Integer add(ChatSession bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ChatSession> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<ChatSession> listBean);

	/**
	 * 根据SessionId查询
	 */
	ChatSession getChatSessionBySessionId(String sessionId);

	/**
	 * 根据SessionId更新
	 */
	Integer updateChatSessionBySessionId(ChatSession bean,String sessionId);

	/**
	 * 根据SessionId删除
	 */
	Integer deleteChatSessionBySessionId(String sessionId);

}