package com.example.yiyuan.service;

import com.example.yiyuan.entity.dto.ScheduleDTO;
import com.example.yiyuan.entity.po.Schedules;
import com.example.yiyuan.entity.query.SchedulesQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.exception.BusinessException;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/27
 *
 */
public interface SchedulesService {
	/**
	 * 根据条件查询列表
	 */
	List<Schedules> findListByParam(SchedulesQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(SchedulesQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Schedules> findListByPage(SchedulesQuery query);

	/**
	 * 新增
	 */
	Integer add(Schedules bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Schedules> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Schedules> listBean);

	/**
	 * 根据Id查询
	 */
	Schedules getSchedulesById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateSchedulesById(Schedules bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteSchedulesById(Integer id);

	List<ScheduleDTO> getAllSchedules(String userId) throws BusinessException;
}