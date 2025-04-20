package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.ChatSessionUser;
import com.example.yiyuan.entity.query.ChatSessionUserQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description:会话用户 Service *
 * @author:??
 * @date:2025/03/06
 *
 */
public interface ChatSessionUserService {
	/**
	 * 根据条件查询列表
	 */
	List<ChatSessionUser> findListByParam(ChatSessionUserQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ChatSessionUserQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ChatSessionUser> findListByPage(ChatSessionUserQuery query);

	/**
	 * 新增
	 */
	Integer add(ChatSessionUser bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ChatSessionUser> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<ChatSessionUser> listBean);

	/**
	 * 根据UserIdAndContactId查询
	 */
	ChatSessionUser getChatSessionUserByUserIdAndContactId(String userId,String contactId);

	/**
	 * 根据UserIdAndContactId更新
	 */
	Integer updateChatSessionUserByUserIdAndContactId(ChatSessionUser bean,String userId,String contactId);

	/**
	 * 根据UserIdAndContactId删除
	 */
	Integer deleteChatSessionUserByUserIdAndContactId(String userId,String contactId);

    void updateRedundanceInfo(String contactName, String contactId);

    void updateByName(String oldName, String newName);
}