package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.query.UserOutlineApplyQuery;
import com.example.yiyuan.entity.vo.DoctorcommentVO;
import com.example.yiyuan.entity.vo.OutlineApplyVO;
import com.example.yiyuan.entity.vo.SymptomCountVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/03/26
 *
 */
public interface UserOutlineApplyMapper<T, P> extends BaseMapper {
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


    List<OutlineApplyVO> loadOnlineApply(UserOutlineApplyQuery query);
	@Select("SELECT symptoms as name, COUNT(*) as value FROM user_outline_apply " +
			"WHERE receive_user_id = #{receiveUserId} GROUP BY symptoms")
    List<SymptomCountVO> countSymptomsByUserId(String userId);

	List<DoctorcommentVO> selectComment(String userId);
}