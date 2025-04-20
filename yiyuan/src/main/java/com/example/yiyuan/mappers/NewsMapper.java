package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.po.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/03/18
 *
 */
public interface NewsMapper<T, P> extends BaseMapper {
	/**
	 * 根据 NewId查询
	 */
	T selectByNewId(@Param("newId") Integer newId);

	/**
	 * 根据 NewId更新
	 */
	Integer updateByNewId(@Param("bean") T t ,@Param("newId") Integer newId);

	/**
	 * 根据 NewId删除
	 */
	Integer deleteByNewId(@Param("newId") Integer newId);

	@Select("SELECT * FROM news WHERE title LIKE CONCAT('%', #{keyWord}, '%')")
	List<News> searchForKeyWord(String keyWord);
	@Select("SELECT new_id AS newId, title, content, author, time, click FROM news")
	List<News> getAllNews();
}