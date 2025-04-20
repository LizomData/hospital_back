package com.example.yiyuan.service;

import com.example.yiyuan.entity.po.Comment;
import com.example.yiyuan.entity.query.CommentQuery;
import com.example.yiyuan.entity.vo.PaginationResultVO;

import java.util.List;

/**
 *
 * @Description: Service *
 * @author:??
 * @date:2025/04/01
 *
 */
public interface CommentService {
	/**
	 * 根据条件查询列表
	 */
	List<Comment> findListByParam(CommentQuery param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(CommentQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Comment> findListByPage(CommentQuery query);

	/**
	 * 新增
	 */
	Integer add(Comment bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<Comment> listBean);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<Comment> listBean);

	/**
	 * 根据Id查询
	 */
	Comment getCommentById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateCommentById(Comment bean,Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteCommentById(Integer id);

}