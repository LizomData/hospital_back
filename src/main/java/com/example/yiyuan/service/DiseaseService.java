package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.Disease;
import com.example.yiyuan.entity.query.DiseaseQuery;
import com.example.yiyuan.entity.vo.CategoryVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/01
 *
 */
public interface DiseaseService {
	/**
	 * 根据条件查询列表
	 */
	List<Disease> findListByParam(DiseaseQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(DiseaseQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Disease> findListByPage(DiseaseQuery query);

	/**
	 * 新增
	 */
	Integer add(Disease bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Disease> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Disease> listBean);

	/**
	 * 根据Id查询
	 */
	Disease getDiseaseById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateDiseaseById(Disease bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteDiseaseById(Integer id);

	List<CategoryVO> getCategory(String patientId);
}