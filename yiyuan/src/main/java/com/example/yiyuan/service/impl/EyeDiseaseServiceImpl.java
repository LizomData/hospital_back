package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.EyeDisease;
import com.example.yiyuan.entity.query.EyeDiseaseQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.EyeDiseaseMapper;
import com.example.yiyuan.service.EyeDiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/07
 *
 */
@Service("eyeDiseaseService")
public class EyeDiseaseServiceImpl implements EyeDiseaseService{

	@Resource
	private EyeDiseaseMapper<EyeDisease,EyeDiseaseQuery> eyeDiseaseMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<EyeDisease> findListByParam(EyeDiseaseQuery query) {
		return this.eyeDiseaseMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(EyeDiseaseQuery query) {
		return this.eyeDiseaseMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<EyeDisease> findListByPage(EyeDiseaseQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<EyeDisease> list = this.findListByParam(query);
		 PaginationResultVO<EyeDisease> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(EyeDisease bean) {
		return this.eyeDiseaseMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<EyeDisease> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.eyeDiseaseMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<EyeDisease> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.eyeDiseaseMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public EyeDisease getEyeDiseaseById(Integer id) {
		 return this.eyeDiseaseMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateEyeDiseaseById(EyeDisease bean,Integer id) {
		 return this.eyeDiseaseMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteEyeDiseaseById(Integer id) {
		 return this.eyeDiseaseMapper.deleteById(id);
	}


}