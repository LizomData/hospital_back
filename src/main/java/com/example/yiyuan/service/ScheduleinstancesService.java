package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.entity.vo.AbleApplyVO;
import com.example.yiyuan.entity.vo.DoctorAllVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/27
 *
 */
public interface ScheduleinstancesService {
	/**
	 * 根据条件查询列表
	 */
	List<Scheduleinstances> findListByParam(ScheduleinstancesQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ScheduleinstancesQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Scheduleinstances> findListByPage(ScheduleinstancesQuery query);

	/**
	 * 新增
	 */
	Integer add(Scheduleinstances bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Scheduleinstances> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Scheduleinstances> listBean);

	/**
	 * 根据Id查询
	 */
	Scheduleinstances getScheduleinstancesById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateScheduleinstancesById(Scheduleinstances bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteScheduleinstancesById(Integer id);

    List<AbleApplyVO> getAnbleApply(String doctorId);

	List<DoctorAllVO> getDoctorAll();
}