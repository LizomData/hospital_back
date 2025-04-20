package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.DoctorAttendance;
import com.example.yiyuan.entity.query.DoctorAttendanceQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.DoctorAttendanceMapper;
import com.example.yiyuan.service.DoctorAttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/30
 *
 */
@Service("doctorAttendanceService")
public class DoctorAttendanceServiceImpl implements DoctorAttendanceService{

	@Resource
	private DoctorAttendanceMapper<DoctorAttendance,DoctorAttendanceQuery> doctorAttendanceMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<DoctorAttendance> findListByParam(DoctorAttendanceQuery query) {
		return this.doctorAttendanceMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(DoctorAttendanceQuery query) {
		return this.doctorAttendanceMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<DoctorAttendance> findListByPage(DoctorAttendanceQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<DoctorAttendance> list = this.findListByParam(query);
		 PaginationResultVO<DoctorAttendance> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(DoctorAttendance bean) {
		return this.doctorAttendanceMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<DoctorAttendance> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.doctorAttendanceMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<DoctorAttendance> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.doctorAttendanceMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public DoctorAttendance getDoctorAttendanceById(Integer id) {
		 return this.doctorAttendanceMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateDoctorAttendanceById(DoctorAttendance bean,Integer id) {
		 return this.doctorAttendanceMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteDoctorAttendanceById(Integer id) {
		 return this.doctorAttendanceMapper.deleteById(id);
	}


}