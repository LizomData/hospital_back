package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.entity.vo.ApplyOnlineVo;
import com.example.yiyuan.entity.vo.MyApplyVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/03/01
 *
 */
public interface UserContactApplyMapper<T, P> extends BaseMapper {
	/**
	 * 根据 ApplyId查询
	 */
	T selectByApplyId(@Param("applyId") Integer applyId);

	/**
	 * 根据 ApplyId更新
	 */
	Integer updateByApplyId(@Param("bean") T t ,@Param("applyId") Integer applyId);

	/**
	 * 根据 ApplyId删除
	 */
	Integer deleteByApplyId(@Param("applyId") Integer applyId);

	/**
	 * 根据 ApplyUserIdAndReceiveUserId查询
	 */
	T selectByApplyUserIdAndReceiveUserId(@Param("applyUserId") String applyUserId, @Param("receiveUserId") String receiveUserId);

	/**
	 * 根据 ApplyUserIdAndReceiveUserId更新
	 */
	Integer updateByApplyUserIdAndReceiveUserId(@Param("bean") T t ,@Param("applyUserId") String applyUserId, @Param("receiveUserId") String receiveUserId);

	/**
	 * 根据 ApplyUserIdAndReceiveUserId删除
	 */
	Integer deleteByApplyUserIdAndReceiveUserId(@Param("applyUserId") String applyUserId, @Param("receiveUserId") String receiveUserId);


    Integer updateByParam(T updateInfo, P applyQuery);


	List<ApplyOnlineVo> loadOnlineApply(@Param("query") UserContactApplyQuery query);


    List<MyApplyVO> findList(String userId);
}