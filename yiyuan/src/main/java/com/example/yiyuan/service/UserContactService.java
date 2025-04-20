package com.example.yiyuan.service;


import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.dto.UserContactSearchResultDto;
import com.example.yiyuan.entity.enums.UserContactStatusEnum;
import com.example.yiyuan.entity.po.UserContact;
import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;

import java.util.List;

/**
 * @Description:联系人 Service *
 * @author:??
 * @date:2025/01/31
 */
public interface UserContactService {
    /**
     * 根据条件查询列表
     */
    List<UserContact> findListByParam(UserContactQuery param);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(UserContactQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<UserContact> findListByPage(UserContactQuery query);

    /**
     * 新增
     */
    Integer add(UserContact bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserContact> listBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<UserContact> listBean);

    /**
     * 根据UserIdAndContactId查询
     */
    UserContact getUserContactByUserIdAndContactId(String userId, String contactId);

    /**
     * 根据UserIdAndContactId更新
     */
    Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId);

    /**
     * 根据UserIdAndContactId删除
     */
    Integer deleteUserContactByUserIdAndContactId(String userId, String contactId);

    UserContactSearchResultDto searchContact(String userId, String contactId) throws BusinessException;

    Integer applyAdd(TokenUserInfoDto tokenUserInfoDto, UserContactApply userContactApply) throws BusinessException;

    void removeUserContact(String userId, String contactId, UserContactStatusEnum statusEnum);

    void addContact4Robot(String userId);
}