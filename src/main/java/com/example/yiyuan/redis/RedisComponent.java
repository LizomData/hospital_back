package com.example.yiyuan.redis;

import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.SysSettingDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.utils.StringTools;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component("redisComponent")
public class RedisComponent {

    @Resource
    private RedisUtils redisUtils;

    public RedisComponent() {
        System.out.println("RedisComponent has been initialized!");
    }

    public void addCommentDoctor(String userId,String doctorId){
        List<String> contactIdList = getCommentDoctor(userId);
        if (contactIdList.contains(doctorId)) {
            return;
        }
        redisUtils.lpush(Constants.REDIS_KEY_COMMENT + userId, doctorId, Constants.REDIS_KEY_TOKEN_EXPIRES*10);

    }
    public List<String> getCommentDoctor(String userId){
        return (List<String>) redisUtils.getQueueList(Constants.REDIS_KEY_COMMENT + userId);
    }
    /**
     * 获取心跳
     *
     * @param userId
     * @return
     */
    public Long getUserHeartBeat(String userId) {
        return (Long) redisUtils.get(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
    }

    public void saveHeartBeat(String userId) {
        redisUtils.setex(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId, System.currentTimeMillis(), Constants.REDIS_KEY_EXPIRES_HEART_BEAT);
    }

    public void removeHeartBeat(String userId) {
        redisUtils.delete(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
    }

    public void saveTokenUserInfoDto(TokenUserInfoDto tokenUserInfoDto) {
        //存用户信息
        redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN + tokenUserInfoDto.getToken(), tokenUserInfoDto, Constants.REDIS_KEY_EXPIRES_DAY);
        //存token
        redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN_USERID + tokenUserInfoDto.getUserId(), tokenUserInfoDto.getToken(), Constants.REDIS_KEY_EXPIRES_DAY);

    }

    public TokenUserInfoDto getTokenUserInfoDto(String token) {
        TokenUserInfoDto tokenUserInfoDto = (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);
        return tokenUserInfoDto;
    }

    public SysSettingDto getSysSetting() {
        SysSettingDto sysSettingDto = (SysSettingDto) redisUtils.get(Constants.REDIS_KEY_SYS_SETTING);
        sysSettingDto = sysSettingDto == null ? new SysSettingDto() : sysSettingDto;
        return sysSettingDto;
    }

    //清空联系人
    public void cleanUserContact(String userId) {
        redisUtils.delete(Constants.REDIS_KEY_USER_CONTACT + userId);
    }

    //批量添加联系人
    public void addUserContactBatch(String userId, List<String> contactIdList) {
        redisUtils.lpushAll(Constants.REDIS_KEY_USER_CONTACT + userId, contactIdList, Constants.REDIS_KEY_TOKEN_EXPIRES);
    }

    public TokenUserInfoDto getTokenUserInfoDtoByUserId(String userId) {
        String token = (String) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN_USERID + userId);
        return getTokenUserInfoDto(token);
    }

    //添加联系人
    public void addUserContact(String userId, String contactId) {
        List<String> contactIdList = getUserContactList(userId);
        if (contactIdList.contains(contactId)) {
            return;
        }
        redisUtils.lpush(Constants.REDIS_KEY_USER_CONTACT + userId, contactId, Constants.REDIS_KEY_TOKEN_EXPIRES);
    }

    public void removeUserContact(String userId, String contactId) {
        String contactListKey = Constants.REDIS_KEY_USER_CONTACT + userId;
        long removedCount = redisUtils.remove(contactListKey, contactId);
        if (removedCount > 0) {
            System.out.println("联系人 " + contactId + " 删除成功!");
        } else {
            System.out.println("联系人 " + contactId + " 不存在!");
        }
    }

    public void cleanUserTokenByUserId(String userId) {
        String token = (String) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN_USERID + userId);
        if (StringTools.isEmpty(token)) {
            return;
        }
        redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN + token);
    }

    //获取所有好友的Id
    public List<String> getUserContactList(String userId) {
        return (List<String>) redisUtils.getQueueList(Constants.REDIS_KEY_USER_CONTACT + userId);
    }
}
