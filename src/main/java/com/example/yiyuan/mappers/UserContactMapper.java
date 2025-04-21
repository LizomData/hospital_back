package com.example.yiyuan.mappers;

import org.apache.ibatis.annotations.Param;
/**
 *
 * @Description:联系人Mapper *
 * @author:??
 * @date:2025/03/01
 *
 */
public interface UserContactMapper<T, P> extends BaseMapper {
	/**
	 * 根据 UserIdAndContactId查询
	 */
	T selectByUserIdAndContactId(@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据 UserIdAndContactId更新
	 */
	Integer updateByUserIdAndContactId(@Param("bean") T t ,@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据 UserIdAndContactId删除
	 */
	Integer deleteByUserIdAndContactId(@Param("userId") String userId, @Param("contactId") String contactId);


}