package com.example.yiyuan.controller;


import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.Scheduleinstances;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.ScheduleinstancesQuery;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.entity.vo.UserInfoVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.redis.RedisUtils;
import com.example.yiyuan.service.ScheduleinstancesService;
import com.example.yiyuan.service.UserInfoService;
import com.example.yiyuan.utils.ScheduleUtils;
import com.example.yiyuan.websocket.MessageHandler;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController("accountController")
@RequestMapping("/account")
@Validated
@Api(tags = "用户端控制器")
public class AccountController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private ScheduleUtils scheduleUtils;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ScheduleinstancesService scheduleinstancesService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private Appconfig appconfig;
    @Resource
    private MessageHandler messageHandler;

    @ApiOperation(value = "查询出院以及就诊人数", notes = "需要从请求头中传递 token 以验证用户身份")
    @GetMapping("/selectPeople")
    public ResponseVO selectPeople(@ApiIgnore HttpServletRequest request,
                                   @ApiParam(name = "type", value = "查找的类型(0为就诊，1为出院)", required = true) @NotNull Integer type) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        userInfoQuery.setStatus(type);
        return getSuccessResponseVO(userInfoService.findCountByParam(userInfoQuery));
    }

    @ApiOperation(value = "获取患者总数", notes = "需要从请求头中传递 token 以验证用户身份")
    @GetMapping("/selectAllPeople")
    public ResponseVO selectAllPeople(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }
        UserInfoQuery userInfoQuery = new UserInfoQuery();
        return getSuccessResponseVO(userInfoService.findCountByParam(userInfoQuery));
    }

    @GetMapping("/orderByAge")
    public ResponseVO orderByAge(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }
        return getSuccessResponseVO(userInfoService.orderByAge());
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping("/checkCode")
    public ResponseVO checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
        String code = captcha.text();
        String checkCodeKey = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey, code, Constants.REDIS_TIME_1MIN * 10);

        String checkCodeBase64 = captcha.toBase64();
        Map<String, String> result = new HashMap<>();
        result.put("checkCode", checkCodeBase64);
        result.put("checkCodeKey", checkCodeKey);
        return getSuccessResponseVO(result);
    }

    @ApiOperation(value = "病人注册接口")
    @PostMapping("/register4User")
    public ResponseVO register4User(@ApiParam(name = "checkCodeKey", value = "验证码唯一标识", required = true) @NotEmpty String checkCodeKey,
                                    @ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
                                    @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password,
                                    @ApiParam(name = "nickName", value = "用户昵称", required = true) @NotEmpty String nickName,
                                    @ApiParam(name = "checkCode", value = "验证码", required = true) @NotEmpty String checkCode) {

        try {
            if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
                return getErrorResponseVO("图片验证码不正确");
            }

            userInfoService.register(email, nickName, password, false);
            return getSuccessResponseVO(null);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } finally {
            redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
        }
    }

    @ApiOperation(value = "病人注册无验证码")
    @PostMapping("/register4UserNo")
    public ResponseVO register4UserNo(@ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
                                      @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password,
                                      @ApiParam(name = "nickName", value = "用户昵称", required = true) @NotEmpty String nickName) throws BusinessException {

        userInfoService.register(email, nickName, password, false);
        return getSuccessResponseVO(null);
    }


    @ApiOperation(value = "医生注册接口")
    @PostMapping("/register4Doctor")
    public ResponseVO register4Doctor(@ApiParam(name = "checkCodeKey", value = "验证码唯一标识", required = true) @NotEmpty String checkCodeKey,
                                      @ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
                                      @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password,
                                      @ApiParam(name = "nickName", value = "用户昵称", required = true) @NotEmpty String nickName,
                                      @ApiParam(name = "checkCode", value = "验证码", required = true) @NotEmpty String checkCode) {

        try {
            if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
                return getErrorResponseVO("图片验证码不正确");
            }

            userInfoService.register(email, nickName, password, true);
            return getSuccessResponseVO(null);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } finally {
            redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
        }
    }

    @ApiOperation(value = "医生注册无验证码")
    @PostMapping("/register4DoctorNo")
    public ResponseVO register4DoctorNo(
            @ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
            @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password,
            @ApiParam(name = "nickName", value = "用户昵称", required = true) @NotEmpty String nickName) throws BusinessException {


        userInfoService.register(email, nickName, password, true);
        return getSuccessResponseVO(null);
    }

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public ResponseVO login(@ApiParam(name = "checkCodeKey", value = "验证码唯一标识", required = true) @NotEmpty String checkCodeKey,
                            @ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
                            @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty String password,
                            @ApiParam(name = "checkCode", value = "验证码", required = true) @NotEmpty String checkCode) {

        try {
            if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
                return getErrorResponseVO("图片验证码不正确");
            }

            UserInfo userInfo = userInfoService.getUserInfoByEmail(email);
            logger.info("userInfo.getUserId()" + userInfo.getUserId());
            if (userInfo.getUserId().charAt(0) == 'D') {
                ScheduleinstancesQuery scheduleinstancesQuery = new ScheduleinstancesQuery();
                scheduleinstancesQuery.setDoctorId(userInfo.getUserId());
                List<Scheduleinstances> scheduleinstances = scheduleinstancesService.findListByParam(scheduleinstancesQuery);
                if (scheduleinstances == null) {
                    scheduleUtils.generateRandomSchedules(userInfo.getUserId());
                }
            }

            UserInfoVO userInfoVO = userInfoService.login(email, password);

            String baseFoder = appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFoder + Constants.FILE_FOLDER_AVATAR_NAME);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePath = targetFileFolder.getPath() + "/" + userInfo.getUserId() + Constants.IMAGE_SUFFIX;
            File avatarFileFolder = new File(filePath);
            if (!avatarFileFolder.exists()) {
                ClassPathResource resource = new ClassPathResource("user.png");
                logger.info("默认头像路径: {}", resource.getURL()); // 打印默认头像路径

                InputStream inputStream = resource.getInputStream();

                MultipartFile multipartFile = new MockMultipartFile(
                        "user",
                        "user.png",
                        "image/png",
                        inputStream
                );

                multipartFile.transferTo(avatarFileFolder);
                logger.info("头像文件保存成功: {}", avatarFileFolder.getAbsolutePath()); // 打印保存成功信息

            }
            return getSuccessResponseVO(userInfoVO);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
        }
    }

    @ApiOperation(value = "登录无验证码接口")
    @PostMapping("/loginNo")
    public ResponseVO loginNo(@ApiParam(name = "email", value = "用户邮箱", required = true) @NotEmpty @Email String email,
                              @ApiParam(name = "password", value = "用户密码", required = true) @NotEmpty String password) throws BusinessException, IOException {

        UserInfo userInfo = userInfoService.getUserInfoByEmail(email);
        if (userInfo.getUserId().charAt(0) == 'D') {
            ScheduleinstancesQuery scheduleinstancesQuery = new ScheduleinstancesQuery();
            scheduleinstancesQuery.setDoctorId(userInfo.getUserId());
            List<Scheduleinstances> scheduleinstances = scheduleinstancesService.findListByParam(scheduleinstancesQuery);

            if (scheduleinstances == null || scheduleinstances.isEmpty()) {
                scheduleUtils.generateRandomSchedules(userInfo.getUserId());
            }
        }
        UserInfoVO userInfoVO = userInfoService.login(email, password);

        String baseFolder = appconfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
        File targetFileFolder = new File(baseFolder + Constants.FILE_FOLDER_AVATAR_NAME);
        if (!targetFileFolder.exists()) {
            targetFileFolder.mkdirs();
        }

        // 规范化文件路径
        String filePath = targetFileFolder.getPath() + File.separator + userInfo.getUserId() + Constants.IMAGE_SUFFIX;
        File avatarFile = new File(filePath);
        logger.info("filePath: {}", filePath); // 打印目标头像路径
        logger.info("avatarFile: {}", avatarFile); // 打印 avatarFile 对象
        if (!avatarFile.exists()) {
            try {
                // 加载默认头像文件
                ClassPathResource resource = new ClassPathResource("user.png");
                logger.info("默认头像路径: {}", resource.getURL()); // 打印默认头像路径

                try (InputStream inputStream = resource.getInputStream()) {
                    // 将资源文件转换为 MultipartFile
                    MultipartFile multipartFile = new MockMultipartFile(
                            "user",
                            "user.png",
                            "image/png",
                            inputStream
                    );

                    // 保存头像文件
                    multipartFile.transferTo(avatarFile);
                    logger.info("头像文件保存成功: {}", avatarFile.getAbsolutePath()); // 打印保存成功信息
                }
            } catch (IOException e) {
                logger.error("头像文件处理失败: {}", e.getMessage(), e); // 打印异常信息
                throw new IOException("头像文件处理失败", e);
            }
        }

        return getSuccessResponseVO(userInfoVO);
    }

    @GlobalInterceptor
    @ApiOperation(value = "获取系统设置接口")
    @GetMapping("/getSysSetting")
    public ResponseVO getSysSetting() {

        return getSuccessResponseVO(redisComponent.getSysSetting());
    }

}
