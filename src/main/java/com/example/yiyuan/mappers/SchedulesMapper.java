package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.dto.ScheduleDTO;
import com.example.yiyuan.entity.po.Schedules;
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
public interface SchedulesMapper<T, P> extends BaseMapper {
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

    List<Schedules> getAllSchedules();
}