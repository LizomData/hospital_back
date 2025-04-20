package com.example.yiyuan.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/03/31
 *
 */
public interface ThingsMapper<T, P> extends BaseMapper {
	/**
	 * 根据 Id查询
	 */
	T selectById(@Param("id") String id);

	/**
	 * 根据 Id更新
	 */
	Integer updateById(@Param("bean") T t ,@Param("id") String id);

	/**
	 * 根据 Id删除
	 */
	Integer deleteById(@Param("id") String id);


}