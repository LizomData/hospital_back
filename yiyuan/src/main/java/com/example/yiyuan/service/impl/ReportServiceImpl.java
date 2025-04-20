package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.dto.ReportRequestDTO;
import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.EyeDisease;
import com.example.yiyuan.entity.po.Report;
import com.example.yiyuan.entity.query.EyeDiseaseQuery;
import com.example.yiyuan.entity.query.ReportQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.EyeDiseaseMapper;
import com.example.yiyuan.mappers.ReportMapper;
import com.example.yiyuan.service.ReportService;
import com.example.yiyuan.utils.StringTools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Service *
 * @author:??
 * @date:2025/04/07
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportMapper<Report, ReportQuery> reportMapper;
    @Resource
    private EyeDiseaseMapper<EyeDisease, EyeDiseaseQuery> eyeDiseaseMapper;

    /**
     * 根据条件查询列表
     */
    public List<Report> findListByParam(ReportQuery query) {
        return this.reportMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ReportQuery query) {
        return this.reportMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<Report> findListByPage(ReportQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<Report> list = this.findListByParam(query);
        PaginationResultVO<Report> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(Report bean) {
        return this.reportMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<Report> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.reportMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<Report> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.reportMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据Id查询
     */
    public Report getReportById(Integer id) {
        return this.reportMapper.selectById(id);
    }

    /**
     * 根据Id更新
     */
    public Integer updateReportById(Report bean, Integer id) {
        return this.reportMapper.updateById(bean, id);
    }

    /**
     * 根据Id删除
     */
    public Integer deleteReportById(Integer id) {
        return this.reportMapper.deleteById(id);
    }

    @Override
    public List<ReportRequestDTO> getReportsByDoctorId(String doctorId) {
        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setDoctorId(doctorId);
        List<Report> reports = reportMapper.selectList(reportQuery);
        List<ReportRequestDTO> result = new ArrayList<>();

        for (Report report : reports) {
            ReportRequestDTO dto = new ReportRequestDTO();
            dto.setPatientId(report.getPatientId());
            dto.setPatientName(report.getPatientName());
            dto.setDoctorId(report.getDoctorId());
            dto.setRecordNo(report.getRecordNo());
            dto.setCheckDate(report.getCheckDate());
            dto.setDate(report.getDate());

            EyeDiseaseQuery eyeDiseaseQuery = new EyeDiseaseQuery();
            eyeDiseaseQuery.setRecordNo(report.getRecordNo());
            List<EyeDisease> diseases = eyeDiseaseMapper.selectList(eyeDiseaseQuery);
            List<ReportRequestDTO.EyeDiseaseDTO> diseaseDTOs = new ArrayList<>();

            for (EyeDisease disease : diseases) {
                ReportRequestDTO.EyeDiseaseDTO d = new ReportRequestDTO.EyeDiseaseDTO();
                d.setCategory(disease.getCategory());
                d.setProbability(Double.valueOf(disease.getProbability()));
                d.setStatus(disease.getStatus());
                d.setDescription(disease.getDescription());
                d.setSuggestion(disease.getSuggestion());
                diseaseDTOs.add(d);
            }

            dto.setEyeDiseases(diseaseDTOs);
            result.add(dto);
        }

        return result;
    }

    @Override
    public ReportRequestDTO getReportsByNo(String doctorId, String recordNo) {

        ReportQuery reportQuery = new ReportQuery();
        reportQuery.setDoctorId(doctorId);
        reportQuery.setRecordNo(recordNo);
        List<Report> reports = reportMapper.selectList(reportQuery);
        if (reports.get(0) == null) {
            return null;
        }
        Report report = reports.get(0);

        ReportRequestDTO dto = new ReportRequestDTO();
        dto.setPatientId(report.getPatientId());
        dto.setPatientName(report.getPatientName());

        dto.setDoctorId(report.getDoctorId());
        dto.setRecordNo(report.getRecordNo());
        dto.setCheckDate(report.getCheckDate());
        dto.setDate(report.getDate());

        EyeDiseaseQuery eyeDiseaseQuery = new EyeDiseaseQuery();
        eyeDiseaseQuery.setRecordNo(report.getRecordNo());
        List<EyeDisease> diseases = eyeDiseaseMapper.selectList(eyeDiseaseQuery);
        List<ReportRequestDTO.EyeDiseaseDTO> diseaseDTOs = new ArrayList<>();

        for (EyeDisease disease : diseases) {
            ReportRequestDTO.EyeDiseaseDTO d = new ReportRequestDTO.EyeDiseaseDTO();
            d.setCategory(disease.getCategory());
            d.setProbability(Double.valueOf(disease.getProbability()));
            d.setStatus(disease.getStatus());
            d.setDescription(disease.getDescription());
            d.setSuggestion(disease.getSuggestion());
            diseaseDTOs.add(d);
        }

        dto.setEyeDiseases(diseaseDTOs);
        return dto;
    }

    @Override
    public void submitComment(String recordNo, String title, String comment) {
        reportMapper.submitComment(recordNo, title, comment);
    }


}