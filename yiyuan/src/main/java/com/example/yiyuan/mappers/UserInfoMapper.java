package com.example.yiyuan.mappers;

import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description:用户信息Mapper *
 * @author:??
 * @date:2025/03/01
 *
 */
public interface UserInfoMapper<T, P> extends BaseMapper {
	/**
	 * 根据 UserId查询
	 */
	T selectByUserId(@Param("userId") String userId);

	/**
	 * 根据 UserId更新
	 */
	Integer updateByUserId(@Param("bean") T t ,@Param("userId") String userId);

	/**
	 * 根据 UserId删除
	 */
	Integer deleteByUserId(@Param("userId") String userId);

	/**
	 * 根据 Email查询
	 */
	T selectByEmail(@Param("email") String email);

	/**
	 * 根据 Email更新
	 */
	Integer updateByEmail(@Param("bean") T t ,@Param("email") String email);

	/**
	 * 根据 Email删除
	 */
	Integer deleteByEmail(@Param("email") String email);

	@Select("SELECT area_name AS name, COUNT(*) AS value FROM user_info GROUP BY area_name")
	List<Map<String, Object>> getUserCountByArea();

	List<Map<String, Integer>> orderByAge();

	@Select("SELECT count(*) from user_outline_apply a left join user_info u on a.apply_user_id = u.user_id WHERE receive_user_id=#{userId} AND u.status = #{type}")
    Integer findCount(String userId, Integer type);

	List<Map<String, Integer>> byAge(String userId);

    List<AnpaiVO> getAnpai(String userId);

	@Select("SELECT user_id as userId,nick_name as nickName,sex,join_type as join_type,area_name as areaName,excellent,grade\n" +
			"from user_info\n" +
			"where user_id = #{userId}")
	UserInfoVO getInfo(String userId);

	List<DoctorVO> getAllDoctor();



	List<AgeVO> getBySex(String userId);

	List<PatientVO> getDetail(String userId, Integer pageNum);

    PatientInfoVO getPatientInfo(String patientId);

	List<Map<String, Object>> selectDailyAppointments(String userId);
	List<Map<String, Object>> selectDailyPatients();
}