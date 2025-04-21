package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.Shifts;
import com.example.yiyuan.entity.query.ShiftsQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/27
 *
 */
public interface ShiftsService {
	/**
	 * 根据条件查询列表
	 */
	List<Shifts> findListByParam(ShiftsQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ShiftsQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Shifts> findListByPage(ShiftsQuery query);

	/**
	 * 新增
	 */
	Integer add(Shifts bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Shifts> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Shifts> listBean);

	/**
	 * 根据Id查询
	 */
	Shifts getShiftsById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateShiftsById(Shifts bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteShiftsById(Integer id);

}