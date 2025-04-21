package com.example.yiyuan.service.impl;


import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.SysSettingDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.dto.UserContactSearchResultDto;
import com.example.yiyuan.entity.enums.*;
import com.example.yiyuan.entity.po.*;
import com.example.yiyuan.entity.query.*;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.*;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.UserContactApplyService;
import com.example.yiyuan.service.UserContactService;
import com.example.yiyuan.utils.CopyTools;
import com.example.yiyuan.utils.StringTools;
import com.example.yiyuan.websocket.ChannelContextUtils;
import com.example.yiyuan.websocket.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:联系人 Service *
 * @author:??
 * @date:2025/01/31
 */
@Service("userContactService")
public class UserContactServiceImpl implements UserContactService {

    private Logger logger = LoggerFactory.getLogger(UserContactServiceImpl.class);
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Resource
    private UserContactApplyService userContactApplyService;
    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private MessageHandler messageHandler;

    /**
     * 根据条件查询列表
     */
    public List<UserContact> findListByParam(UserContactQuery query) {
        return this.userContactMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(UserContactQuery query) {
        return this.userContactMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<UserContact> findListByPage(UserContactQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<UserContact> list = this.findListByParam(query);
        PaginationResultVO<UserContact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(UserContact bean) {
        return this.userContactMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<UserContact> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据UserIdAndContactId查询
     */
    public UserContact getUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
    }

    /**
     * 根据UserIdAndContactId更新
     */
    public Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId) {
        return this.userContactMapper.updateByUserIdAndContactId(bean, userId, contactId);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    public Integer deleteUserContactByUserIdAndContactId(String userId, String contactId) {
        return this.userContactMapper.deleteByUserIdAndContactId(userId, contactId);
    }

    @Override
    public UserContactSearchResultDto searchContact(String userId, String contactId) throws BusinessException {

        UserContactSearchResultDto resultDto = new UserContactSearchResultDto();
        UserInfo userInfo = userInfoMapper.selectByUserId(contactId);
        if (userInfo == null) {
            throw new BusinessException("未找到该医生");
        }
        resultDto = CopyTools.copy(userInfo, UserContactSearchResultDto.class);
        resultDto.setContactId(contactId);
        if (userId.equals(contactId)) {
            resultDto.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            return resultDto;
        }
        //查询是否为好友
        UserContact userContact = this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
        resultDto.setStatus(userContact == null ? UserContactStatusEnum.NOT_FRIEND.getStatus() : userContact.getStatus());
        return resultDto;
    }

    //用户发给医生
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer applyAdd(TokenUserInfoDto tokenUserInfoDto, UserContactApply userContactApply) throws BusinessException {

        //申请人
        String applyUserId = tokenUserInfoDto.getUserId();

        String applyInfo = userContactApply.getApplyInfo();
        //默认申请信息
        applyInfo = StringTools.isEmpty(applyInfo) ? String.format(Constants.APPLY_INFO_TEMPLATE, tokenUserInfoDto.getNickName()) : applyInfo;

        Long curTime = System.currentTimeMillis();
        logger.info("当前时间戳是:" + curTime);

        //加人时候的申请类型
        Integer joinType = null;
        String receiveUserId = userContactApply.getReceiveUserId();

        UserContactApply userContactApply1 = userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
        Integer number = 0;
        //申请次数
        if (userContactApply1 != null) {
            number = Integer.valueOf(userContactApply1.getNumber());

        }
        //查询对方好友是否已经添加，如果已经拉黑则无法添加
        UserContact userContact = userContactMapper.selectByUserIdAndContactId(applyUserId, receiveUserId);
        if (userContact != null && UserContactStatusEnum.BLACKLIST_BE.getStatus().equals(userContact.getStatus())) {
            throw new BusinessException("你已经被对方拉黑，无法对其进行咨询");
        }

        UserInfo userInfo = userInfoMapper.selectByUserId(receiveUserId);
        if (userInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        joinType = userInfo.getJoinType();

        //直接加入不用记录申请记录
        if (JoinTypeEnum.JOIN.getType().equals(joinType)) {
            userContactApplyService.addContact(applyUserId, receiveUserId, applyInfo);
            return joinType;
        }

        UserContactApply dbApply = this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
        if (dbApply == null) {

            userContactApply.setApplyUserId(applyUserId);
            userContactApply.setLastApplyTime(curTime);
            userContactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());

            userContactApplyMapper.insert(userContactApply);

        } else {
            //更新状态
            userContactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
            userContactApply.setLastApplyTime(curTime);
            userContactApply.setNumber(String.valueOf(number + 1));
            userContactApplyMapper.updateByApplyId(userContactApply, dbApply.getApplyId());
        }
        UserContactApply userApply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);

        if (dbApply == null || !UserContactApplyStatusEnum.INIT.getStatus().equals(dbApply.getStatus())) {
            MessageSendDto messageSendDto = new MessageSendDto();
            messageSendDto.setMessageType(MessageTypeEnum.CONTACT_APPLY.getType());
            messageSendDto.setMessageContent(applyInfo);
            messageSendDto.setContactId(receiveUserId);
            messageHandler.sendMessage(messageSendDto);
        }
        return userApply.getApplyId();
    }

    @Override
    public void removeUserContact(String userId, String contactId, UserContactStatusEnum statusEnum) {
        //移除好友
        UserContact userContact = new UserContact();
        userContact.setStatus(statusEnum.getStatus());
        userContact.setLastUpdateTime(new Date());
        userContactMapper.updateByUserIdAndContactId(userContact, userId, contactId);

        //将好友中也移除我自己
        UserContact friendContact = new UserContact();
        friendContact.setLastUpdateTime(new Date());
        if (UserContactStatusEnum.DEL == statusEnum) {
            friendContact.setStatus(UserContactStatusEnum.DEL_BE.getStatus());
        } else if (UserContactStatusEnum.BLACKLIST == statusEnum) {
            friendContact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
        }
        userContactMapper.updateByUserIdAndContactId(friendContact, contactId, userId);
        redisComponent.removeUserContact(userId, contactId);
        redisComponent.removeUserContact(contactId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addContact4Robot(String userId) {
        Date curDate = new Date();
        SysSettingDto sysSettingDto = redisComponent.getSysSetting();
        String contactId = sysSettingDto.getRobotUid();
        String contactName = sysSettingDto.getRobotNickName();
        String sendMessage = sysSettingDto.getRobotWelcome();
        sendMessage = StringTools.cleanHtmlTag(sendMessage);

        //添加机器人好友
        UserContact userContact = new UserContact();
        userContact.setUserId(userId);
        userContact.setContactId(contactId);
        userContact.setCreateTime(curDate);
        userContact.setLastUpdateTime(curDate);
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContactMapper.insert(userContact);
        //增加会话信息
        String sessionId = StringTools.getChatSessionId4User(new String[]{userId, contactId});
        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(sendMessage);
        chatSession.setSessionId(sessionId);
        chatSession.setLastReceiveTime(curDate.getTime());
        chatSessionMapper.insert(chatSession);

        //增加会话人信息
        ChatSessionUser chatSessionUser = new ChatSessionUser();
        chatSessionUser.setUserId(userId);
        chatSessionUser.setContactId(contactId);
        chatSessionUser.setContactName(contactName);
        chatSessionUser.setSessionId(sessionId);
        chatSessionUserMapper.insert(chatSessionUser);

        //增加聊天消息
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setMessageContent(sendMessage);
        chatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
        chatMessage.setSendUserId(contactId);
        chatMessage.setSendUserNickName(contactName);
        chatMessage.setSendTime(curDate.getTime());
        chatMessage.setContactId(userId);
        chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
        chatMessageMapper.insert(chatMessage);
    }
}