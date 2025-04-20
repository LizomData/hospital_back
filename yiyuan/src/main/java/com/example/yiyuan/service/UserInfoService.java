package com.example.yiyuan.service;



import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.entity.vo.*;
import com.example.yiyuan.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:用户信息 Service *
 * @author:??
 * @date:2025/01/26
 */
public interface UserInfoService {
    /**
     * 根据条件查询列表
     */
    List<UserInfo> findListByParam(UserInfoQuery param);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(UserInfoQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query);

    /**
     * 新增
     */
    Integer add(UserInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserInfo> listBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<UserInfo> listBean);

    /**
     * 根据UserId查询
     */
    UserInfo getUserInfoByUserId(String userId);

    /**
     * 根据UserId更新
     */
    Integer updateUserInfoByUserId(UserInfo bean, String userId);

    /**
     * 根据UserId删除
     */
    Integer deleteUserInfoByUserId(String userId);

    /**
     * 根据Email查询
     */
    UserInfo getUserInfoByEmail(String email);

    /**
     * 根据Email更新
     */
    Integer updateUserInfoByEmail(UserInfo bean, String email);

    /**
     * 根据Email删除
     */
    Integer deleteUserInfoByEmail(String email);

    void register(String email, String nickName, String password,Boolean a) throws BusinessException;
    UserInfoVO login(String email, String password) throws BusinessException;
    void updateUserInfo(UserInfo userInfo, MultipartFile avatarFile) throws IOException;
    List<Map<String, Object>> getUserCountGroupedByArea();

    List<Map<String, Integer>> orderByAge();

    Integer findCount(String userId, Integer type);

    List<Map<String, Integer>>  byAge(String userId);

    List<AnpaiVO> getAnpai(String userId);

    UserInfoVO getInfo(String userId);

    List<DoctorVO> getAllDoctor();

    List<AgeVO> getBySex(String userId);

    List<PatientVO> getDetail(String userId, Integer pageNum);

    PatientInfoVO getPatientInfo(String patientId);

    Object getDailyStatistics(String userId);
}