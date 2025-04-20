package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.Things;
import com.example.yiyuan.entity.query.ThingsQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/31
 *
 */
public interface ThingsService {
	/**
	 * 根据条件查询列表
	 */
	List<Things> findListByParam(ThingsQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ThingsQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Things> findListByPage(ThingsQuery query);

	/**
	 * 新增
	 */
	Integer add(Things bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Things> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Things> listBean);

	/**
	 * 根据Id查询
	 */
	Things getThingsById(String id);

	/**
	 * 根据Id更新
	 */
	Integer updateThingsById(Things bean,String id);

	/**
	 * 根据Id删除
	 */
	Integer deleteThingsById(String id);

}