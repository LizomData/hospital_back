package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.DoctorAttendance;
import com.example.yiyuan.entity.query.DoctorAttendanceQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/30
 *
 */
public interface DoctorAttendanceService {
	/**
	 * 根据条件查询列表
	 */
	List<DoctorAttendance> findListByParam(DoctorAttendanceQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(DoctorAttendanceQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<DoctorAttendance> findListByPage(DoctorAttendanceQuery query);

	/**
	 * 新增
	 */
	Integer add(DoctorAttendance bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<DoctorAttendance> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<DoctorAttendance> listBean);

	/**
	 * 根据Id查询
	 */
	DoctorAttendance getDoctorAttendanceById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateDoctorAttendanceById(DoctorAttendance bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteDoctorAttendanceById(Integer id);

}