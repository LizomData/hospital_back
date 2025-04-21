package com.example.yiyuan.service.impl;


import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.SysSettingDto;
import com.example.yiyuan.entity.enums.*;
import com.example.yiyuan.entity.po.*;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.vo.MyApplyVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.entity.vo.ApplyOnlineVo;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.*;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.UserContactApplyService;
import com.example.yiyuan.utils.CopyTools;
import com.example.yiyuan.utils.StringTools;
import com.example.yiyuan.websocket.MessageHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: Service *
 * @author:??
 * @date:2025/01/31
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

    Logger logger = LoggerFactory.getLogger(UserContactApplyServiceImpl.class);
    @Resource
    private Appconfig appconfig;
    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;

    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private RedisComponent redisComponent;

    @Resource
    private ChatSessionMapper chatSessionMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ChatSessionUserMapper chatSessionUserMapper;
    @Resource
    private ChatMessageMapper chatMessageMapper;
    @Resource
    private MessageHandler messageHandler;

    /**
     * 根据条件查询列表
     */
    public List<UserContactApply> findListByParam(UserContactApplyQuery query) {
        return this.userContactApplyMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(UserContactApplyQuery query) {
        return this.userContactApplyMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<UserContactApply> list = this.findListByParam(query);
        PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(UserContactApply bean) {
        return this.userContactApplyMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据ApplyId查询
     */
    public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 根据ApplyId更新
     */
    public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
        return this.userContactApplyMapper.updateByApplyId(bean, applyId);
    }

    /**
     * 根据ApplyId删除
     */
    public Integer deleteUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.deleteByApplyId(applyId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId查询
     */
    public UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
        return this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId更新
     */
    public Integer updateUserContactApplyByApplyUserIdAndReceiveUserId(UserContactApply bean, String applyUserId, String receiveUserId) {
        return this.userContactApplyMapper.updateByApplyUserIdAndReceiveUserId(bean, applyUserId, receiveUserId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserId删除
     */
    public Integer deleteUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
        return this.userContactApplyMapper.deleteByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
    }


    //这里的userId是本人的id
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealWithApply(String userId, Integer applyId, Integer status) throws BusinessException {
        UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
        if (statusEnum == null || UserContactApplyStatusEnum.INIT == statusEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        //有的不能直接传过来id就把那个删除，还是要判断的
        UserContactApply applyInfo = userContactApplyMapper.selectByApplyId(applyId);
        if (applyInfo == null || !userId.equals(applyInfo.getReceiveUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserContactApply updateInfo = new UserContactApply();
        updateInfo.setStatus(statusEnum.getStatus());
        updateInfo.setLastApplyTime(System.currentTimeMillis());

        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setApplyId(applyId);
        applyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        //确保状态只能从0改到其他的，不能改状态为其他的

        Integer count = userContactApplyMapper.updateByParam(updateInfo, applyQuery);
        if (count == 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (UserContactApplyStatusEnum.PASS.getStatus().equals(status)) {
            addContact(applyInfo.getApplyUserId(), applyInfo.getReceiveUserId(), applyInfo.getApplyInfo());
            return;
        }
        if (UserContactApplyStatusEnum.BLACKLIST.getStatus().equals(status)) {
            Date curDate = new Date();
            UserContact userContact = new UserContact();
            userContact.setUserId(applyInfo.getApplyUserId());
            userContact.setContactId(applyInfo.getReceiveUserId());
            userContact.setCreateTime(curDate);
            userContact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
            userContact.setLastUpdateTime(curDate);
            userContactMapper.insertOrUpdate(userContact);
        }


    }

    //添加联系人
    @Override
    public void addContact(String applyUserId, String receiveUserId, String applyInfo) throws BusinessException {

        Date curDate = new Date();
        //同意，双方添加好友
        List<UserContact> contactList = new ArrayList<>();
        //申请人添加对方
        UserContact userContact = new UserContact();
        userContact.setUserId(applyUserId);
        userContact.setContactId(receiveUserId);
        userContact.setCreateTime(curDate);
        userContact.setLastUpdateTime(curDate);
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        contactList.add(userContact);
        //如果是申请好友，接收人添加申请人，意思就是你加我之后我还要加你
        userContact = new UserContact();
        userContact.setUserId(receiveUserId);
        userContact.setContactId(applyUserId);
        userContact.setCreateTime(curDate);
        userContact.setLastUpdateTime(curDate);
        userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        contactList.add(userContact);

        //批量插入
        userContactMapper.insertOrUpdateBatch(contactList);

        redisComponent.addUserContact(receiveUserId, applyUserId);
        redisComponent.addUserContact(applyUserId, receiveUserId);
        //创建会话并且插入数据库
        String sessionId = StringTools.getChatSessionId4User(new String[]{applyUserId, receiveUserId});
        List<ChatSessionUser> chatSessionUserList = new ArrayList<>();
        //存最后一条信息的
        ChatSession chatSession = new ChatSession();
        chatSession.setSessionId(sessionId);
        chatSession.setLastMessage(applyInfo);
        chatSession.setLastReceiveTime(curDate.getTime());
        chatSessionMapper.insertOrUpdate(chatSession);

        //申请人session 包括双方的Id还有session还有昵称
        ChatSessionUser applySessionUser = new ChatSessionUser();
        applySessionUser.setUserId(applyUserId);
        applySessionUser.setContactId(receiveUserId);
        applySessionUser.setSessionId(sessionId);
        UserInfo contactUser = (UserInfo) userInfoMapper.selectByUserId(receiveUserId);
        applySessionUser.setContactName(contactUser.getNickName());
        chatSessionUserList.add(applySessionUser);

        //接受人session 包括双方的Id还有session还有昵称
        ChatSessionUser contactSessionUser = new ChatSessionUser();
        contactSessionUser.setUserId(receiveUserId);
        contactSessionUser.setContactId(applyUserId);
        contactSessionUser.setSessionId(sessionId);
        UserInfo applyUser = (UserInfo) userInfoMapper.selectByUserId(applyUserId);
        contactSessionUser.setContactName(applyUser.getNickName());
        chatSessionUserList.add(contactSessionUser);
        chatSessionUserMapper.insertOrUpdateBatch(chatSessionUserList);

        //记录消息表
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSessionId(sessionId);
        chatMessage.setMessageType(MessageTypeEnum.ADD_FRIEND.getType());
        chatMessage.setMessageContent(applyInfo);
        chatMessage.setSendUserId(applyUserId);
        chatMessage.setSendUserNickName(applyUser.getNickName());
        chatMessage.setSendTime(curDate.getTime());
        chatMessage.setContactId(receiveUserId);
        chatMessageMapper.insert(chatMessage);

        MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);
        //发送给接受还有申请的人
        messageHandler.sendMessage(messageSendDto);

        messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEND_SELF.getType());
        messageSendDto.setContactId(applyUserId);
        //接收人信息
        messageSendDto.setExtendData(contactUser);
        messageHandler.sendMessage(messageSendDto);

    }

    @Override
    public void saveApplyFile(String userId, int i, Integer applyId, MultipartFile file) throws BusinessException {
        UserContactApply userContactApply = userContactApplyMapper.selectByApplyId(applyId);
        logger.info("userContactApply+" + userContactApply);
        if (userContactApply == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (userContactApply == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (!userContactApply.getApplyUserId().equals(userId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SysSettingDto sysSettingDto = redisComponent.getSysSetting();
        String fileSuffix = StringTools.getFileSuffix(file.getOriginalFilename());
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
        String fileName = file.getOriginalFilename();
        logger.info("上传fileName:" + fileName);
        String fileExtName = StringTools.getFileSuffix(fileName);
        logger.info("上传fileExtName:" + fileExtName);
        //12.jpg
        String fileRealName = applyId + "_" + (i + 1) + ".jpg";
        logger.info("上传fileRealName:" + fileRealName);
        File folder = new File(appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_APPLY_NAME);
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

    }

    @Override
    public List<MyApplyVO> findList(String userId) {
        return userContactApplyMapper.findList(userId);
    }

    public List<ApplyOnlineVo> loadOnlineApply(UserContactApplyQuery query) {
        return userContactApplyMapper.loadOnlineApply(query);
    }
}