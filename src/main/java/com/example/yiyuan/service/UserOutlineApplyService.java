package com.example.yiyuan.service;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.UserOutlineApply;
import com.example.yiyuan.entity.query.UserOutlineApplyQuery;
import com.example.yiyuan.entity.vo.DoctorcommentVO;
import com.example.yiyuan.entity.vo.OutlineApplyVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.entity.vo.SymptomCountVO;
import com.example.yiyuan.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/26
 *
 */
public interface UserOutlineApplyService {
	/**
	 * 根据条件查询列表
	 */
	List<UserOutlineApply> findListByParam(UserOutlineApplyQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(UserOutlineApplyQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserOutlineApply> findListByPage(UserOutlineApplyQuery query);

	/**
	 * 新增
	 */
	Integer add(UserOutlineApply bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserOutlineApply> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<UserOutlineApply> listBean);

	/**
	 * 根据ApplyId查询
	 */
	UserOutlineApply getUserOutlineApplyByApplyId(Integer applyId);

	/**
	 * 根据ApplyId更新
	 */
	Integer updateUserOutlineApplyByApplyId(UserOutlineApply bean,Integer applyId);

	/**
	 * 根据ApplyId删除
	 */
	Integer deleteUserOutlineApplyByApplyId(Integer applyId);

	/**
	 * 根据ApplyUserIdAndReceiveUserId查询
	 */
	UserOutlineApply getUserOutlineApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId);

	/**
	 * 根据ApplyUserIdAndReceiveUserId更新
	 */
	Integer updateUserOutlineApplyByApplyUserIdAndReceiveUserId(UserOutlineApply bean,String applyUserId,String receiveUserId);

	/**
	 * 根据ApplyUserIdAndReceiveUserId删除
	 */
	Integer deleteUserOutlineApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId);

    void saveApplyFile(String userId, int i, Integer applyId, MultipartFile fileo) throws BusinessException;

    List<OutlineApplyVO> loadOnlineApply(UserOutlineApplyQuery userContactApplyQuery);

    List<SymptomCountVO> countSymptomsByUserId(String userId);

    List<DoctorcommentVO> selectComment(String userId);
}