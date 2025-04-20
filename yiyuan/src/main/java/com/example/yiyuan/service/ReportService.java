package com.example.yiyuan.service;

import com.example.yiyuan.entity.dto.ReportRequestDTO;
import com.example.yiyuan.entity.po.Report;
import com.example.yiyuan.entity.query.ReportQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/07
 *
 */
public interface ReportService {
	/**
	 * 根据条件查询列表
	 */
	List<Report> findListByParam(ReportQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ReportQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Report> findListByPage(ReportQuery query);

	/**
	 * 新增
	 */
	Integer add(Report bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Report> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Report> listBean);

	/**
	 * 根据Id查询
	 */
	Report getReportById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateReportById(Report bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteReportById(Integer id);

    List<ReportRequestDTO> getReportsByDoctorId(String doctorId);

	ReportRequestDTO getReportsByNo(String doctorId, String recordNo);

    void submitComment(String recordNo, String title, String comment);
}