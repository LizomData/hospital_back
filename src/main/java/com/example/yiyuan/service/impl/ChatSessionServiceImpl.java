package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.ChatSession;
import com.example.yiyuan.entity.query.ChatSessionQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.ChatSessionMapper;
import com.example.yiyuan.service.ChatSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description:会话信息 Service *
 * @author:??
 * @date:2025/03/06
 *
 */
@Service("chatSessionService")
public class ChatSessionServiceImpl implements ChatSessionService{

	@Resource
	private ChatSessionMapper<ChatSession,ChatSessionQuery> chatSessionMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<ChatSession> findListByParam(ChatSessionQuery query) {
		return this.chatSessionMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ChatSessionQuery query) {
		return this.chatSessionMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<ChatSession> findListByPage(ChatSessionQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<ChatSession> list = this.findListByParam(query);
		 PaginationResultVO<ChatSession> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(ChatSession bean) {
		return this.chatSessionMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<ChatSession> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.chatSessionMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<ChatSession> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.chatSessionMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据SessionId查询
	 */
	public ChatSession getChatSessionBySessionId(String sessionId) {
		 return this.chatSessionMapper.selectBySessionId(sessionId);
	}

	/**
	 * 根据SessionId更新
	 */
	public Integer updateChatSessionBySessionId(ChatSession bean,String sessionId) {
		 return this.chatSessionMapper.updateBySessionId(bean,sessionId);
	}

	/**
	 * 根据SessionId删除
	 */
	public Integer deleteChatSessionBySessionId(String sessionId) {
		 return this.chatSessionMapper.deleteBySessionId(sessionId);
	}


}