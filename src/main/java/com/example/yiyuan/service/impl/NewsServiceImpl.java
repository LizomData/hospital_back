package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.News;
import com.example.yiyuan.entity.query.NewsQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.NewsMapper;
import com.example.yiyuan.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/18
 *
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService{

	@Resource
	private NewsMapper<News,NewsQuery> newsMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<News> findListByParam(NewsQuery query) {
		return this.newsMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(NewsQuery query) {
		return this.newsMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<News> findListByPage(NewsQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<News> list = this.findListByParam(query);
		 PaginationResultVO<News> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(News bean) {
		return this.newsMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<News> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.newsMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<News> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.newsMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据NewId查询
	 */
	public News getNewsByNewId(Integer newId) {
		 return this.newsMapper.selectByNewId(newId);
	}

	/**
	 * 根据NewId更新
	 */
	public Integer updateNewsByNewId(News bean,Integer newId) {
		 return this.newsMapper.updateByNewId(bean,newId);
	}

	/**
	 * 根据NewId删除
	 */
	public Integer deleteNewsByNewId(Integer newId) {
		 return this.newsMapper.deleteByNewId(newId);
	}

	@Override
	public List<News> searchForKeyWord(String keyWord) {
		return newsMapper.searchForKeyWord(keyWord);
	}

	@Override
	public List<News> getAllNews() {
		return newsMapper.getAllNews();
	}


}