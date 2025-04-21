package com.example.yiyuan.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Description:Mapper *
 * @author:??
 * @date:2025/04/07
 */
public interface ReportMapper<T, P> extends BaseMapper {
    /**
     * 根据 Id查询
     */
    T selectById(@Param("id") Integer id);

    /**
     * 根据 Id更新
     */
    Integer updateById(@Param("bean") T t, @Param("id") Integer id);

    /**
     * 根据 Id删除
     */
    Integer deleteById(@Param("id") Integer id);

    @Update("UPDATE report\n" +
            "    SET comment = #{comment},\n" +
            "        title = #{title}\n" +
            "    WHERE recordNo = #{recordNo}")
    void submitComment(String recordNo, String title, String comment);
}