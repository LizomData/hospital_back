package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.News;
import com.example.yiyuan.entity.query.NewsQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/03/18
 *
 */
public interface NewsService {
	/**
	 * 根据条件查询列表
	 */
	List<News> findListByParam(NewsQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(NewsQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<News> findListByPage(NewsQuery query);

	/**
	 * 新增
	 */
	Integer add(News bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<News> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<News> listBean);

	/**
	 * 根据NewId查询
	 */
	News getNewsByNewId(Integer newId);

	/**
	 * 根据NewId更新
	 */
	Integer updateNewsByNewId(News bean,Integer newId);

	/**
	 * 根据NewId删除
	 */
	Integer deleteNewsByNewId(Integer newId);

	List<News> searchForKeyWord(String keyWord);

    List<News> getAllNews();
}