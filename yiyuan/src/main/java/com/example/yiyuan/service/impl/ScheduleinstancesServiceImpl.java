package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.AbleApplyVO;
import com.example.yiyuan.entity.vo.DoctorAllVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.ScheduleinstancesMapper;
import com.example.yiyuan.service.ScheduleinstancesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/27
 *
 */
@Service("scheduleinstancesService")
public class ScheduleinstancesServiceImpl implements ScheduleinstancesService{

	@Resource
	private ScheduleinstancesMapper<Scheduleinstances,ScheduleinstancesQuery> scheduleinstancesMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Scheduleinstances> findListByParam(ScheduleinstancesQuery query) {
		return this.scheduleinstancesMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ScheduleinstancesQuery query) {
		return this.scheduleinstancesMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Scheduleinstances> findListByPage(ScheduleinstancesQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<Scheduleinstances> list = this.findListByParam(query);
		 PaginationResultVO<Scheduleinstances> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Scheduleinstances bean) {
		return this.scheduleinstancesMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<Scheduleinstances> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.scheduleinstancesMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<Scheduleinstances> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.scheduleinstancesMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Scheduleinstances getScheduleinstancesById(Integer id) {
		 return this.scheduleinstancesMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateScheduleinstancesById(Scheduleinstances bean,Integer id) {
		 return this.scheduleinstancesMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteScheduleinstancesById(Integer id) {
		 return this.scheduleinstancesMapper.deleteById(id);
	}

	@Override
	public List<AbleApplyVO> getAnbleApply(String doctorId) {
		return scheduleinstancesMapper.getAnbleApply(doctorId);
	}

	@Override
	public List<DoctorAllVO> getDoctorAll() {
		return scheduleinstancesMapper.getDoctorAll();
	}


}