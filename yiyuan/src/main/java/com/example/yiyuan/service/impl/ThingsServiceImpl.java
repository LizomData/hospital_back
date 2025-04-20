package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Things;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.ThingsQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.ThingsMapper;
import com.example.yiyuan.service.ThingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/31
 *
 */
@Service("thingsService")
public class ThingsServiceImpl implements ThingsService{

	@Resource
	private ThingsMapper<Things,ThingsQuery> thingsMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Things> findListByParam(ThingsQuery query) {
		return this.thingsMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ThingsQuery query) {
		return this.thingsMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Things> findListByPage(ThingsQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<Things> list = this.findListByParam(query);
		 PaginationResultVO<Things> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Things bean) {
		return this.thingsMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<Things> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.thingsMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<Things> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.thingsMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Things getThingsById(String id) {
		 return this.thingsMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateThingsById(Things bean,String id) {
		 return this.thingsMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteThingsById(String id) {
		 return this.thingsMapper.deleteById(id);
	}


}