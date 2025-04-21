package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.EyeDisease;
import com.example.yiyuan.entity.query.EyeDiseaseQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/07
 *
 */
public interface EyeDiseaseService {
	/**
	 * 根据条件查询列表
	 */
	List<EyeDisease> findListByParam(EyeDiseaseQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(EyeDiseaseQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<EyeDisease> findListByPage(EyeDiseaseQuery query);

	/**
	 * 新增
	 */
	Integer add(EyeDisease bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<EyeDisease> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<EyeDisease> listBean);

	/**
	 * 根据Id查询
	 */
	EyeDisease getEyeDiseaseById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateEyeDiseaseById(EyeDisease bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteEyeDiseaseById(Integer id);

}