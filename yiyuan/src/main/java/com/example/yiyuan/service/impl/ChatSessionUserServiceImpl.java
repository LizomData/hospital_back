package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.enums.MessageTypeEnum;
import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.enums.UserContactStatusEnum;
import com.example.yiyuan.entity.enums.UserContactTypeEnum;
import com.example.yiyuan.entity.po.ChatSessionUser;
import com.example.yiyuan.entity.po.UserContact;
import com.example.yiyuan.entity.query.ChatSessionUserQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.ChatSessionUserMapper;
import com.example.yiyuan.mappers.UserContactMapper;
import com.example.yiyuan.service.ChatSessionUserService;
import com.example.yiyuan.websocket.MessageHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:会话用户 Service *
 * @author:??
 * @date:2025/03/06
 */
@Service("chatSessionUserService")
public class ChatSessionUserServiceImpl implements ChatSessionUserService {

    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private MessageHandler messageHandler;

    /**
     * 根据条件查询列表
     */
    public List<ChatSessionUser> findListByParam(ChatSessionUserQuery query) {
        return this.chatSessionUserMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ChatSessionUserQuery query) {
        return this.chatSessionUserMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ChatSessionUser> findListByPage(ChatSessionUserQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ChatSessionUser> list = this.findListByParam(query);
        PaginationResultVO<ChatSessionUser> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ChatSessionUser bean) {
        return this.chatSessionUserMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ChatSessionUser> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatSessionUserMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ChatSessionUser> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatSessionUserMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据UserIdAndContactId查询
     */
    public ChatSessionUser getChatSessionUserByUserIdAndContactId(String userId, String contactId) {
        return this.chatSessionUserMapper.selectByUserIdAndContactId(userId, contactId);
    }

    /**
     * 根据UserIdAndContactId更新
     */
    public Integer updateChatSessionUserByUserIdAndContactId(ChatSessionUser bean, String userId, String contactId) {
        return this.chatSessionUserMapper.updateByUserIdAndContactId(bean, userId, contactId);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    public Integer deleteChatSessionUserByUserIdAndContactId(String userId, String contactId) {
        return this.chatSessionUserMapper.deleteByUserIdAndContactId(userId, contactId);
    }

    @Override
    public void updateRedundanceInfo(String contactName, String contactId) {
        ChatSessionUser updateInfo = new ChatSessionUser();
        updateInfo.setContactName(contactName);

        ChatSessionUserQuery chatSessionUserQuery = new ChatSessionUserQuery();
        chatSessionUserQuery.setContactId(contactId);
        chatSessionUserMapper.updateByParam(updateInfo, chatSessionUserQuery);
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setContactId(contactId);
        userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        List<UserContact> userContactList = userContactMapper.selectList(userContactQuery);
        for (UserContact userContact : userContactList) {
            MessageSendDto messageSendDto = new MessageSendDto();
            messageSendDto.setContactId(userContact.getUserId());
            messageSendDto.setExtendData(contactName);
            messageSendDto.setMessageType(MessageTypeEnum.CONTACT_NAME_UPDATE.getType());
            messageSendDto.setSendUserId(contactId);
            messageSendDto.setSendUserNickName(contactName);
            messageHandler.sendMessage(messageSendDto);
        }
    }

    @Override
    public void updateByName(String oldName, String newName) {
        chatSessionUserMapper.updateByName(oldName,newName);
    }
}