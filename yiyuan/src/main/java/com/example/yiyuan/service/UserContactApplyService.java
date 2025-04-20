package com.example.yiyuan.service;


import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.entity.vo.ApplyOnlineVo;
import com.example.yiyuan.entity.vo.MyApplyVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: Service *
 * @author:??
 * @date:2025/02/06
 */
public interface UserContactApplyService {
    /**
     * 根据条件查询列表
     */
    List<UserContactApply> findListByParam(UserContactApplyQuery param);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(UserContactApplyQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery query);

    /**
     * 新增
     */
    Integer add(UserContactApply bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserContactApply> listBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<UserContactApply> listBean);

    /**
     * 根据ApplyId查询
     */
    UserContactApply getUserContactApplyByApplyId(Integer applyId);

    /**
     * 根据ApplyId更新
     */
    Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId);

    /**
     * 根据ApplyId删除
     */
    Integer deleteUserContactApplyByApplyId(Integer applyId);

    /**
     * 根据ApplyUserIdAndReceiveUserId查询
     */
    UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId);

    /**
     * 根据ApplyUserIdAndReceiveUserId更新
     */
    Integer updateUserContactApplyByApplyUserIdAndReceiveUserId(UserContactApply bean,String applyUserId,String receiveUserId);

    /**
     * 根据ApplyUserIdAndReceiveUserId删除
     */
    Integer deleteUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId);

    void dealWithApply(String userId, Integer applyId, Integer status) throws BusinessException;

    void addContact(String applyUserId, String receiveUserId, String applyInfo) throws BusinessException;
    public List<ApplyOnlineVo> loadOnlineApply(UserContactApplyQuery query);
    void saveApplyFile(String userId, int i, Integer applyId, MultipartFile fileo) throws BusinessException;

    List<MyApplyVO> findList(String userId);
}