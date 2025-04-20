package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.dto.ScheduleInstanceDTO;
import com.example.yiyuan.entity.vo.AbleApplyVO;
import com.example.yiyuan.entity.vo.DoctorAllVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/03/27
 *
 */
public interface ScheduleinstancesMapper<T, P> extends BaseMapper {
	/**
	 * 根据 Id查询
	 */
	T selectById(@Param("id") Integer id);

	/**
	 * 根据 Id更新
	 */
	Integer updateById(@Param("bean") T t ,@Param("id") Integer id);

	/**
	 * 根据 Id删除
	 */
	Integer deleteById(@Param("id") Integer id);

	@Select("SELECT * FROM scheduleinstances WHERE schedule_id = #{id} AND doctor_id = #{userId}")
    List<ScheduleInstanceDTO> getInstancesByScheduleId(int id, String userId);

    List<AbleApplyVO> getAnbleApply(String doctorId);

	List<DoctorAllVO> getDoctorAll();
}