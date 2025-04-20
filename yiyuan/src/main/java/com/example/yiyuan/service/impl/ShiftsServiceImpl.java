package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Shifts;
import com.example.yiyuan.entity.query.ShiftsQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.ShiftsMapper;
import com.example.yiyuan.service.ShiftsService;
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
@Service("shiftsService")
public class ShiftsServiceImpl implements ShiftsService{

	@Resource
	private ShiftsMapper<Shifts,ShiftsQuery> shiftsMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Shifts> findListByParam(ShiftsQuery query) {
		return this.shiftsMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ShiftsQuery query) {
		return this.shiftsMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Shifts> findListByPage(ShiftsQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<Shifts> list = this.findListByParam(query);
		 PaginationResultVO<Shifts> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Shifts bean) {
		return this.shiftsMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<Shifts> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.shiftsMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<Shifts> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.shiftsMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Shifts getShiftsById(Integer id) {
		 return this.shiftsMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateShiftsById(Shifts bean,Integer id) {
		 return this.shiftsMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteShiftsById(Integer id) {
		 return this.shiftsMapper.deleteById(id);
	}


}