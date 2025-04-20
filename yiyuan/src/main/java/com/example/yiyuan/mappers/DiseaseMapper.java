package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @Description:Mapper *
 * @author:??
 * @date:2025/04/01
 *
 */
public interface DiseaseMapper<T, P> extends BaseMapper {
	/**
	 * 根据 Id查询
	 */
	T selectById(@Param("id") Integer id);

	/**
	 * 根据 Id更新
	 */
	Integer updateById(@Param("bean") T t ,@Param("id") Integer id);

	/**
	 * 根据 Id删除
	 */
	Integer deleteById(@Param("id") Integer id);


	List<CategoryVO> getCategory(String patientId);
}