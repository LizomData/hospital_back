package com.example.yiyuan.service.impl;


import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.StatResultDTO;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.*;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.po.ChatSessionUser;
import com.example.yiyuan.entity.po.UserContact;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.ChatMessageQuery;
import com.example.yiyuan.entity.query.SimplePage;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.entity.vo.*;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.UserContactMapper;
import com.example.yiyuan.mappers.UserInfoMapper;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.ChatMessageService;
import com.example.yiyuan.service.ChatSessionUserService;
import com.example.yiyuan.service.UserContactService;
import com.example.yiyuan.service.UserInfoService;
import com.example.yiyuan.utils.CopyTools;
import com.example.yiyuan.utils.StringTools;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:用户信息 Service *
 * @author:??
 * @date:2025/01/26
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private UserContactService userContactService;

    @Resource
    private Appconfig appconfig;

    @Resource
    private RedisComponent redisComponent;
    @Resource
    private ChatSessionUserService chatSessionUserService;
    @Resource
    private ChatMessageService chatMessageService;


    /**
     * 根据条件查询列表
     */
    public List<UserInfo> findListByParam(UserInfoQuery query) {
        return this.userInfoMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(UserInfoQuery query) {
        return this.userInfoMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery query) {
        Integer count = this.findCountByParam(query);
        Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(query);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据UserId查询
     */
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId更新
     */
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据Email查询
     */
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email更新
     */
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String email, String nickName, String password, Boolean a) throws BusinessException {
        Map<String, Object> result = new HashMap<>();
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        String userId = null;
        if (null != userInfo) {
            throw new BusinessException("邮箱账号已经存在");
        }
        if (a) {
            userId = StringTools.getDoctorId();
        } else {
            userId = StringTools.getUserId();

        }

        Date curDate = new Date();
        userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);
        userInfo.setPassword(StringTools.encodeMd5(password));
        userInfo.setCreateTime(curDate);
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setLastOffTime(System.currentTimeMillis());
        userInfo.setJoinType(JoinTypeEnum.APPLY.getType());
        userInfoMapper.insert(userInfo);

        userContactService.addContact4Robot(userId);
    }

    @Override
    public UserInfoVO login(String email, String password) throws BusinessException {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null == userInfo || !userInfo.getPassword().equals(StringTools.encodeMd5(password))) {
            throw new BusinessException("账号或密码不存在");
        }

        if (userInfo.getStatus().equals(UserStatusEnum.DISABLE)) {
            throw new BusinessException("账号已禁用");
        }
        //查询我的联系人
        UserContactQuery contactQuery = new UserContactQuery();
        contactQuery.setUserId(userInfo.getUserId());
        contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        List<UserContact> contactList = userContactMapper.selectList(contactQuery);
        List<String> contactIdList = contactList.stream().map(item -> item.getContactId()).collect(Collectors.toList());
        redisComponent.cleanUserContact(userInfo.getUserId());
        if (!contactIdList.isEmpty()) {
            redisComponent.addUserContactBatch(userInfo.getUserId(), contactIdList);
        }

        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto(userInfo);

        Long lastHeartBeat = redisComponent.getUserHeartBeat(userInfo.getUserId());
        if (lastHeartBeat != null) {
            throw new BusinessException("此账号已经在别处登录，请退出后再登录");
        }
        //保存登录信息到redis中
        String token = StringTools.encodeMd5(tokenUserInfoDto.getUserId() + StringTools.getRandomString(Constants.LENGTH_20));
        tokenUserInfoDto.setToken(token);
        redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);

        UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);
        userInfoVO.setToken(token);
        return userInfoVO;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfo userInfo, MultipartFile avatarFile) throws IOException {
        if (avatarFile != null) {
            String baseFoder = appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFoder + Constants.FILE_FOLDER_AVATAR_NAME);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePath = targetFileFolder.getPath() + "/" + userInfo.getUserId() + Constants.IMAGE_SUFFIX;
            avatarFile.transferTo(new File(filePath));
        }

        UserInfo dbInfo = userInfoMapper.selectByUserId(userInfo.getUserId());
        String oldName = dbInfo.getNickName();
        String newName = userInfo.getNickName();
        userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());
        String contactNameUpdate = null;
        if (!dbInfo.getNickName().equals(userInfo.getNickName())) {
            contactNameUpdate = userInfo.getNickName();
        }
        if (!Objects.equals(oldName, newName)) {
            chatMessageService.updateChatMessageByName(oldName, newName);
            chatSessionUserService.updateByName(oldName, newName);
        }
        if (contactNameUpdate == null) {
            return;
        }
        //更新token中昵称
        TokenUserInfoDto tokenUserInfoDto = redisComponent.getTokenUserInfoDtoByUserId(userInfo.getUserId());
        tokenUserInfoDto.setNickName(contactNameUpdate);
        redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
        chatSessionUserService.updateRedundanceInfo(contactNameUpdate, userInfo.getUserId());
    }

    private TokenUserInfoDto getTokenUserInfoDto(UserInfo userInfo) {
        TokenUserInfoDto tokenUserInfoDto = new TokenUserInfoDto();
        tokenUserInfoDto.setUserId(userInfo.getUserId());
        tokenUserInfoDto.setNickName(userInfo.getNickName());

        String adminEmails = appconfig.getAdminEmails();
        String[] emailArray = adminEmails.split(",");
        if (!StringTools.isEmpty(adminEmails) && ArrayUtils.contains(emailArray, userInfo.getEmail())) {
            tokenUserInfoDto.setAdmin(true);
        } else {
            tokenUserInfoDto.setAdmin(false);
        }
        return tokenUserInfoDto;
    }

    public List<Map<String, Object>> getUserCountGroupedByArea() {
        return userInfoMapper.getUserCountByArea();
    }

    @Override
    public List<Map<String, Integer>> orderByAge() {
        return userInfoMapper.orderByAge();
    }

    @Override
    public Integer findCount(String userId, Integer type) {
        return userInfoMapper.findCount(userId, type);
    }

    @Override
    public List<Map<String, Integer>> byAge(String userId) {
        return userInfoMapper.byAge(userId);
    }

    @Override
    public List<AnpaiVO> getAnpai(String userId) {
        return userInfoMapper.getAnpai(userId);
    }

    @Override
    public UserInfoVO getInfo(String userId) {
        return userInfoMapper.getInfo(userId);
    }

    @Override
    public List<DoctorVO> getAllDoctor() {
        return userInfoMapper.getAllDoctor();
    }

    @Override
    public List<AgeVO> getBySex(String userId) {

        return userInfoMapper.getBySex(userId);
    }


    public String randomTreatment() {
        Random random = new Random();
        int treatmentNumber = random.nextInt(3) + 1; // 生成1-3的随机数

        switch (treatmentNumber) {
            case 1:
                return "第一疗程";
            case 2:
                return "第二疗程";
            case 3:
                return "第三疗程";
            default:
                return "基础疗程";
        }
    }

    @Override
    public List<PatientVO> getDetail(String userId, Integer pageNum) {
        List<PatientVO> patientVOS = userInfoMapper.getDetail(userId, pageNum);
        for (PatientVO patientVO : patientVOS) {
            patientVO.setProgress(randomTreatment());
        }
        return patientVOS;
    }

    @Override
    public PatientInfoVO getPatientInfo(String patientId) {
        return userInfoMapper.getPatientInfo(patientId);
    }

    public StatResultDTO getDailyStatistics(String userId) {
        // 1. 查询原始数据
        List<Map<String, Object>> appointments = userInfoMapper.selectDailyAppointments(userId);
        List<Map<String, Object>> patients = userInfoMapper.selectDailyPatients();

        // 2. 转换为日期->数量的映射
        Map<LocalDate, Integer> appointmentMap = parseResult(appointments);
        Map<LocalDate, Integer> patientMap = parseResult(patients);

        // 3. 获取日期范围
        List<LocalDate> dateRange = getDateRange(appointmentMap, patientMap);

        // 4. 填充结果
        return StatResultDTO.builder()
                .dateList(formatDates(dateRange))
                .appointmentList(fillCounts(dateRange, appointmentMap))
                .patientList(fillCounts(dateRange, patientMap))
                .build();
    }

    private Map<LocalDate, Integer> parseResult(List<Map<String, Object>> rawData) {
        return rawData.stream()
                .collect(Collectors.toMap(
                        item -> LocalDate.parse(item.get("statDate").toString()),
                        item -> ((Long) item.get("count")).intValue()
                ));
    }

    private List<LocalDate> getDateRange(Map<LocalDate, Integer>... dataMaps) {
        // 1. 获取最小日期（Java 8 兼容写法）
        Optional<LocalDate> minDate = Stream.of(dataMaps)
                .flatMap(map -> map.keySet().stream())
                .min(LocalDate::compareTo);

        // 2. 获取最大日期（Java 8 兼容写法）
        Optional<LocalDate> maxDate = Stream.of(dataMaps)
                .flatMap(map -> map.keySet().stream())
                .max(LocalDate::compareTo);

        // 3. 检查空值（替代 isEmpty()）
        if (!minDate.isPresent() || !maxDate.isPresent()) {
            return Collections.emptyList();
        }

        // 4. 生成日期范围（替代 datesUntil）
        List<LocalDate> dateRange = new ArrayList<>();
        LocalDate current = minDate.get();
        LocalDate end = maxDate.get().plusDays(1);

        while (current.isBefore(end)) {
            dateRange.add(current);
            current = current.plusDays(1);
        }

        return dateRange;
    }

    private List<String> formatDates(List<LocalDate> dates) {
        return dates.stream()
                .map(date -> date.format(DateTimeFormatter.ISO_DATE))
                .collect(Collectors.toList());
    }

    private List<Integer> fillCounts(List<LocalDate> dateRange, Map<LocalDate, Integer> dataMap) {
        return dateRange.stream()
                .map(date -> dataMap.getOrDefault(date, 0))
                .collect(Collectors.toList());
    }
}
