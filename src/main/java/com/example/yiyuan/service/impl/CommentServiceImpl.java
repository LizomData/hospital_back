package com.example.yiyuan.service.impl;

import com.example.yiyuan.entity.enums.PageSize;
import com.example.yiyuan.entity.po.Comment;
import com.example.yiyuan.entity.query.CommentQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.vo.PaginationResultVO;
import com.example.yiyuan.mappers.CommentMapper;
import com.example.yiyuan.service.CommentService;
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
@Service("commentService")
public class CommentServiceImpl implements CommentService{

	@Resource
	private CommentMapper<Comment,CommentQuery> commentMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Comment> findListByParam(CommentQuery query) {
		return this.commentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(CommentQuery query) {
		return this.commentMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Comment> findListByPage(CommentQuery query) {
		 Integer count=this.findCountByParam(query);
		 Integer pageSize = query.getPageSize()==null? PageSize.SIZE15.getSize():query.getPageSize();
		 SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		 query.setSimplePage(page);
		 List<Comment> list = this.findListByParam(query);
		 PaginationResultVO<Comment> result = new PaginationResultVO(count, page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);
		 return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Comment bean) {
		return this.commentMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<Comment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.commentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<Comment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.commentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据Id查询
	 */
	public Comment getCommentById(Integer id) {
		 return this.commentMapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateCommentById(Comment bean,Integer id) {
		 return this.commentMapper.updateById(bean,id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteCommentById(Integer id) {
		 return this.commentMapper.deleteById(id);
	}


}