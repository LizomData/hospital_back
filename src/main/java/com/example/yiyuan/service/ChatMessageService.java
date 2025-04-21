package com.example.yiyuan.service;

import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.query.ChatMessageQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @Description:聊天消息表 Service *
 * @author:??
 * @date:2025/03/06
 */
public interface ChatMessageService {
    /**
     * 根据条件查询列表
     */
    List<ChatMessage> findListByParam(ChatMessageQuery param);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(ChatMessageQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<ChatMessage> findListByPage(ChatMessageQuery query);

    /**
     * 新增
     */
    Integer add(ChatMessage bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ChatMessage> listBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<ChatMessage> listBean);

    /**
     * 根据MessageId查询
     */
    ChatMessage getChatMessageByMessageId(Long messageId);

    /**
     * 根据MessageId更新
     */
    Integer updateChatMessageByMessageId(ChatMessage bean, Long messageId);

    /**
     * 根据MessageId删除
     */
    Integer deleteChatMessageByMessageId(Long messageId);

    MessageSendDto saveMessage(ChatMessage chatMessage, TokenUserInfoDto tokenUserInfoDto) throws BusinessException;

    void saveMessageFile(String userId, Long messageId, MultipartFile file) throws BusinessException;

    File downloadFile(TokenUserInfoDto userInfoDto, Long fileId, Boolean showCover) throws BusinessException;

    void updateChatMessageByName(String oldName, String newName);

    List<ChatMessage> getMessageList(String sessionId, String userId);
}