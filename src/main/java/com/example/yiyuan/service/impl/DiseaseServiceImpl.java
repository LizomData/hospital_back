package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Disease;
import com.example.yiyuan.entity.query.DiseaseQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.CategoryVO;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.DiseaseMapper;
import com.example.yiyuan.service.DiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/01
 *
 */
@Service("diseaseService")
public class DiseaseServiceImpl implements DiseaseService{

	@Resource
	private DiseaseMapper<Disease,DiseaseQuery> diseaseMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Disease> findListByParam(DiseaseQuery query) {
		return this.diseaseMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(DiseaseQuery query) {
		return this.diseaseMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Disease> findListByPage(DiseaseQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<Disease> list = this.findListByParam(query);
		 PaginationResultVO<Disease> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Disease bean) {
		return this.diseaseMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<Disease> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.diseaseMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<Disease> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.diseaseMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Disease getDiseaseById(Integer id) {
		 return this.diseaseMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateDiseaseById(Disease bean,Integer id) {
		 return this.diseaseMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteDiseaseById(Integer id) {
		 return this.diseaseMapper.deleteById(id);
	}

	@Override
	public List<CategoryVO> getCategory(String patientId) {
		return diseaseMapper.getCategory(patientId);
	}


}