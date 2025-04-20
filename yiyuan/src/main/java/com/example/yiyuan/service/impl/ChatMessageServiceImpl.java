package com.example.yiyuan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.SysSettingDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.*;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.po.ChatSession;
import com.example.yiyuan.entity.po.UserContact;
import com.example.yiyuan.entity.query.ChatMessageQuery;
import com.example.yiyuan.entity.query.ChatSessionQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.ChatMessageMapper;
import com.example.yiyuan.mappers.ChatSessionMapper;
import com.example.yiyuan.mappers.UserContactMapper;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.ChatMessageService;
import com.example.yiyuan.utils.CopyTools;
import com.example.yiyuan.utils.DateUtils;
import com.example.yiyuan.utils.StringTools;
import com.example.yiyuan.websocket.MessageHandler;
import com.sun.org.apache.bcel.internal.generic.DUP;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description:聊天消息表 Service *
 * @author:??
 * @date:2025/03/06
 */
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {
    Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
    @Resource
    private ChatServiceImpl chatService;
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;

    @Resource
    private MessageHandler messageHandler;

    @Resource
    private Appconfig appconfig;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private XfxhServiceImpl xfxhService;

    /**
     * 根据条件查询列表
     */
    public List<ChatMessage> findListByParam(ChatMessageQuery query) {
        return this.chatMessageMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ChatMessageQuery query) {
        return this.chatMessageMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ChatMessage> findListByPage(ChatMessageQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ChatMessage> list = this.findListByParam(query);
        PaginationResultVO<ChatMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ChatMessage bean) {
        return this.chatMessageMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据MessageId查询
     */
    public ChatMessage getChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.selectByMessageId(messageId);
    }

    /**
     * 根据MessageId更新
     */
    public Integer updateChatMessageByMessageId(ChatMessage bean, Long messageId) {
        return this.chatMessageMapper.updateByMessageId(bean, messageId);
    }

    /**
     * 根据MessageId删除
     */
    public Integer deleteChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.deleteByMessageId(messageId);
    }

    @Override
    public MessageSendDto saveMessage(ChatMessage chatMessage, TokenUserInfoDto tokenUserInfoDto) throws BusinessException {

        //排除机器人发消息以及给非好友发消息
        if (!Constants.ROBOT_UID.equals(tokenUserInfoDto.getUserId())) {
            List<String> contactList = redisComponent.getUserContactList(tokenUserInfoDto.getUserId());

            if (!contactList.contains(chatMessage.getContactId())) {
                throw new BusinessException(ResponseCodeEnum.CODE_902);
            }
        }
        String sessionId = StringTools.getChatSessionId4User(new String[]{tokenUserInfoDto.getUserId(), chatMessage.getContactId()});
        String sendUserId = tokenUserInfoDto.getUserId();
        String contactId = chatMessage.getContactId();
        chatMessage.setSessionId(sessionId);
        Long curTime = System.currentTimeMillis();
        chatMessage.setSendTime(curTime);

        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(chatMessage.getMessageType());
        if (null == messageTypeEnum || !ArrayUtils.contains(new Integer[]{MessageTypeEnum.CHAT.getType(), MessageTypeEnum.MEDIA_CHAT.getType()}, chatMessage.getMessageType())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        Integer status = MessageTypeEnum.MEDIA_CHAT == messageTypeEnum ? MessageStatusEnum.SENDING.getStatus() : MessageStatusEnum.SENDED.getStatus();
        chatMessage.setStatus(status);
        String messageContent = StringTools.cleanHtmlTag(chatMessage.getMessageContent());
        chatMessage.setMessageContent(messageContent);

        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(messageContent);
        chatSession.setLastReceiveTime(curTime);
        chatSessionMapper.updateBySessionId(chatSession, sessionId);

        //开始发消息
        chatMessage.setSendUserId(sendUserId);
        chatMessage.setSendUserNickName(tokenUserInfoDto.getNickName());
        chatMessageMapper.insert(chatMessage);
        MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);

        logger.info("getMessageType" + chatMessage.getMessageType());

        if (Constants.ROBOT_UID.equals(contactId) && chatMessage.getMessageType() == 2) {
            SysSettingDto sysSettingDto = redisComponent.getSysSetting();
            TokenUserInfoDto robot = new TokenUserInfoDto();
            robot.setUserId(sysSettingDto.getRobotUid());
            robot.setNickName(sysSettingDto.getRobotNickName());
            ChatMessage robotChatMessage = new ChatMessage();
            robotChatMessage.setContactId(sendUserId);
            //这里可以接ai哦
            String answer = ChatServiceImpl.chat(chatMessage.getMessageContent());
            logger.info("answer" + answer);
            robotChatMessage.setMessageContent(answer);
            robotChatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
            saveMessage(robotChatMessage, robot);
        } else {
            messageHandler.sendMessage(messageSendDto);
        }
        return messageSendDto;
    }

    @Override
    public void saveMessageFile(String userId, Long messageId, MultipartFile file) throws BusinessException {
        ChatMessage chatMessage = chatMessageMapper.selectByMessageId(messageId);
        logger.info("userId" + userId);
        logger.info("chatMessage.getSendUserId()" + chatMessage.getSendUserId());
        if (!chatMessage.getSendUserId().equals(userId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SysSettingDto sysSettingDto = redisComponent.getSysSetting();
        String fileSuffix = StringTools.getFileSuffix(file.getOriginalFilename());
        logger.info(fileSuffix);
        if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxImageSize() * Constants.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxVideoSize() * Constants.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && !ArrayUtils.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
                && !ArrayUtils.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
                && file.getSize() > sysSettingDto.getMaxFileSize() * Constants.FILE_SIZE_MB
        ) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        String fileRealName = messageId + ".jpg";
        logger.info("上传fileRealName:" + fileRealName);
        File folder = new File(appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_MESSAGE_NAME);
        logger.info("上传appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE " + appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_MESSAGE_NAME);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        File uploadFile = new File(folder.getPath() + "/" + fileRealName);
        logger.info("上传uploadFile:" + uploadFile);

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            throw new BusinessException("文件上传失败");
        }
        ChatMessage uploadInfo = new ChatMessage();
        uploadInfo.setStatus(MessageStatusEnum.SENDED.getStatus());
        ChatMessageQuery messageQuery = new ChatMessageQuery();
        messageQuery.setMessageId(messageId);
        chatMessageMapper.updateByParam(uploadInfo, messageQuery);

        if (chatMessage.getContactId().equals(Constants.ROBOT_UID)) {
            String text = chatMessage.getMessageContent();
            String imageUrl = Constants.SERVER_MESSAGE_PATH + messageId + ".jpg";
            logger.info("text:" + text);
            logger.info("imageUrl:" + imageUrl);

            String content = chatService.chatPhoto(text, imageUrl);
            logger.info("content:" + content);

            TokenUserInfoDto robot = new TokenUserInfoDto();
            robot.setUserId(sysSettingDto.getRobotUid());
            robot.setNickName(sysSettingDto.getRobotNickName());
            ChatMessage robotChatMessage = new ChatMessage();
            robotChatMessage.setContactId(userId);

            robotChatMessage.setMessageContent(content);
            robotChatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
            saveMessage(robotChatMessage, robot);
        }
    }

    @Override
    public File downloadFile(TokenUserInfoDto userInfoDto, Long messageId, Boolean showCover) throws BusinessException {
        ChatMessage message = chatMessageMapper.selectByMessageId(messageId);
        String contactId = message.getContactId();
        if (!userInfoDto.getUserId().equals(message.getContactId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        String month = DateUtils.format(new Date(message.getSendTime()), DateTimePatternEnum.YYYYMM.getPattern());
        File folder = new File(appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + month);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = message.getFileName();
        logger.info("下载fileName:" + fileName);

        String fileExtName = StringTools.getFileSuffix(fileName);
        logger.info("下载fileExtName:" + fileExtName);

        String fileRealName = messageId + fileExtName;
        logger.info("下载fileRealName:" + fileRealName);
        logger.info("下载fileRealName封面:" + fileRealName + Constants.COVER_IMAGE_SUFFIX);

        if (showCover != null && showCover) {
            fileRealName = fileRealName + Constants.COVER_IMAGE_SUFFIX;
        }
        File file = new File(folder.getPath() + "/" + fileRealName);
        logger.info("下载fileRealName:" + folder.getPath() + "/" + fileRealName);

        if (!file.exists()) {
            logger.info("文件不存在{}", messageId);
            throw new BusinessException(ResponseCodeEnum.CODE_602);
        }


        return file;
    }

    @Override
    public void updateChatMessageByName(String oldName, String newName) {
        chatMessageMapper.updateChatMessageByName(oldName, newName);
    }

    @Override
    public List<ChatMessage> getMessageList(String sessionId, String userId) {
        return chatMessageMapper.getMessageList(sessionId, userId);
    }
}