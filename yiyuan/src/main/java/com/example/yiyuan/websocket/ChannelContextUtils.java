package com.example.yiyuan.websocket;

import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.WsInitData;
import com.example.yiyuan.entity.enums.MessageTypeEnum;
import com.example.yiyuan.entity.enums.UserContactApplyStatusEnum;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.po.ChatSessionUser;
import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.ChatMessageQuery;
import com.example.yiyuan.entity.query.ChatSessionUserQuery;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.mappers.*;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.ChatSessionUserService;
import com.example.yiyuan.utils.JsonUtils;
import com.example.yiyuan.utils.StringTools;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import javassist.convert.TransformWriteField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChannelContextUtils {
    private static final Logger logger = LoggerFactory.getLogger(ChannelContextUtils.class);
    private static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private ChatSessionUserService chatSessionUserService;
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;

    //在 Netty 的 Channel 里存储 userId
    public void addContext(String userId, Channel channel) {
        String channelId = channel.id().toString();
        //AttributeKey<T> 是 Netty 提供的 "附加属性存储"
        AttributeKey attributeKey = null;
        if (!AttributeKey.exists(channelId)) {
            attributeKey = AttributeKey.newInstance(channelId);
        } else {
            attributeKey = AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(userId);

        USER_CONTEXT_MAP.put(userId, channel);
        redisComponent.saveHeartBeat(userId);

        //更新用户最后连接的时间
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        userInfoMapper.updateByUserId(updateInfo, userId);

        //给用户发送消息
        UserInfo userInfo = (UserInfo) userInfoMapper.selectByUserId(userId);
        Long sourceLastOffTime = userInfo.getLastOffTime();
        Long lastOffTime = sourceLastOffTime;
        if (sourceLastOffTime != null && System.currentTimeMillis() - Constants.MILLISSECONDS_3DAYS_AGO > sourceLastOffTime) {
            lastOffTime = System.currentTimeMillis() - Constants.MILLISSECONDS_3DAYS_AGO;

        }

        //查询会话信息
        ChatSessionUserQuery sessionUserQuery = new ChatSessionUserQuery();
        sessionUserQuery.setUserId(userId);
        sessionUserQuery.setOrderBy("last_receive_time desc");
        List<ChatSessionUser> chatSessionUserList = chatSessionUserService.findListByParam(sessionUserQuery);

        WsInitData wsInitData = new WsInitData();
        wsInitData.setChatSessionList(chatSessionUserList);

        //查询聊天信息
        List<String> contactIdList = redisComponent.getUserContactList(userId);
        ChatMessageQuery messageQuery = new ChatMessageQuery();
        messageQuery.setContactIdList(contactIdList);
        messageQuery.setContactId(userId);
        messageQuery.setLastReceiveTime(lastOffTime);
        List<ChatMessage> chatMessageList = chatMessageMapper.selectList(messageQuery);
        wsInitData.setChatMessageList(chatMessageList);

        //查询好友申请
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setReceiveUserId(userId);
        applyQuery.setLastApplyTimestamp(lastOffTime);
        logger.error("System.currentTimeMillis():" + System.currentTimeMillis());
        logger.error("lastOffTime:" + lastOffTime);
        applyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        Integer applyCount = userContactApplyMapper.selectCount(applyQuery);
        logger.error("applycount是" + applyCount);
        wsInitData.setApplyCount(applyCount);


        //发送信息
        MessageSendDto messageSendDto = new MessageSendDto();
        messageSendDto.setMessageType(MessageTypeEnum.INIT.getType());
        messageSendDto.setContactId(userId);
        messageSendDto.setExtendData(wsInitData);

//        sendMsg(messageSendDto, userId);
    }

    public void sendMessage(MessageSendDto messageSendDto) {
        send2User(messageSendDto);
    }

    private void send2User(MessageSendDto messageSendDto) {
        String contactId = messageSendDto.getContactId();
        if (StringTools.isEmpty(contactId)) {
            return;
        }
        sendMsg(messageSendDto, contactId);
        //强制下线
        if (MessageTypeEnum.FORCE_OFF_LINE.getType().equals(messageSendDto.getMessageType())) {
            closeContext(contactId);
        }
    }

    public void closeContext(String userId) {
        if (StringTools.isEmpty(userId)) {
            return;
        }
        redisComponent.cleanUserTokenByUserId(userId);
        Channel channel = USER_CONTEXT_MAP.get(userId);
        if (channel == null) {
            return;
        }
        channel.close();
    }

    //发送信息
    public void sendMsg(MessageSendDto messageSendDto, String receiveId) {
        if (receiveId == null) {
            return;
        }
        Channel userChannel = USER_CONTEXT_MAP.get(receiveId);
        if (userChannel == null) {
            return;
        }
        if (MessageTypeEnum.ADD_FRIEND_SELF.getType().equals(messageSendDto.getMessageType())) {
            //userInfo是接受人(医生)
            UserInfo userInfo = (UserInfo) messageSendDto.getExtendData();
            messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEND.getType());
            messageSendDto.setContactId(userInfo.getUserId());
            messageSendDto.setContactName(userInfo.getNickName());
            messageSendDto.setExtendData(null);
        } else {
            messageSendDto.setContactId(messageSendDto.getSendUserId());
            messageSendDto.setContactName(messageSendDto.getSendUserNickName());

        }

        userChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.convertObj2Json(messageSendDto)));
    }

    public void removeContext(Channel channel) {
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attribute.get();
        if (!StringTools.isEmpty(userId)) {
            USER_CONTEXT_MAP.remove(userId);
        }
        redisComponent.removeHeartBeat(userId);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastOffTime(System.currentTimeMillis());

        userInfoMapper.updateByUserId(updateInfo, userId);
    }
}
