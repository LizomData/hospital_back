package com.example.yiyuan.controller;

import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.UserContactStatusEnum;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.vo.AnpaiVO;
import com.example.yiyuan.entity.vo.DoctorVO;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.entity.vo.UserInfoVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.service.UserInfoService;
import com.example.yiyuan.utils.CopyTools;
import com.example.yiyuan.utils.StringTools;
import com.example.yiyuan.websocket.ChannelContextUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@Api(tags = "个人控制器")
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private ChannelContextUtils channelContextUtils;

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO getUserInfo(@ApiIgnore HttpServletRequest request) {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserInfoVO userInfoVO = userInfoService.getInfo(tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(userInfoVO);

    }

    @GetMapping("/getAllDoctor")
    @GlobalInterceptor
    public ResponseVO getAllDoctor() {

        String[] universities = {
                "University A",
                "University B",
                "University C",
                "University D",
                "University E"
        };

        List<DoctorVO> doctorVO = userInfoService.getAllDoctor();

        for (DoctorVO doctor : doctorVO) {
            Random random = new Random();

            doctor.setNumber(random.nextInt(401) + 600);
            doctor.setPrice(random.nextInt(601)+900);
            double percentage = 95.0 + (98.0 - 95.0) * random.nextDouble();
            DecimalFormat df = new DecimalFormat("0.0");
            String percentageStr = df.format(percentage) + "%";
            doctor.setGood(percentageStr);

            int index = random.nextInt(universities.length);
            String randomUniversity = universities[index];
            doctor.setSchool(randomUniversity);

        }
        return getSuccessResponseVO(doctorVO);

    }

    @GetMapping("/getAnpai")
    @GlobalInterceptor
    public ResponseVO getAnpai(@ApiIgnore HttpServletRequest request) {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        List<AnpaiVO> anpaiVO = userInfoService.getAnpai(userId);
        return getSuccessResponseVO(anpaiVO);
    }


    @PostMapping("/saveUserInfo")
    @ApiOperation(value = "修改用户信息", notes = "需要从请求头中传递 token 以验证用户身份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "avatarFile", value = "用户头像文件", dataType = "__file", paramType = "form"),
    })
    @GlobalInterceptor
    public ResponseVO saveUserInfo(@ApiIgnore HttpServletRequest request,
                                   @ApiParam(name = "userInfo", value = "用户修改的信息", required = true) @NotNull UserInfo userInfo,
                                   @ApiParam(name = "avatarFile", value = "用户头像") MultipartFile avatarFile
    ) throws IOException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        userInfo.setUserId(tokenUserInfoDto.getUserId());
        userInfo.setPassword(null);
        userInfo.setStatus(null);
        userInfo.setCreateTime(null);
        userInfo.setLastLoginTime(null);
        userInfoService.updateUserInfo(userInfo, avatarFile);
        return getUserInfo(request);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "更新密码", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO updatePassword(@ApiIgnore HttpServletRequest request,
                                     @ApiParam(name = "password", value = "密码", required = true) @NotEmpty @Pattern(regexp = Constants.REGEX_PASSWORD) String password) throws IOException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(StringTools.encodeMd5(password));
        userInfoService.updateUserInfoByUserId(userInfo, tokenUserInfoDto.getUserId());
        channelContextUtils.closeContext(tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO logout(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        channelContextUtils.closeContext(tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }

    @GetMapping("/areaCount")
    public ResponseVO getUserCountByArea() {
        List<Map<String, Object>> result = userInfoService.getUserCountGroupedByArea();
        ;
        return getSuccessResponseVO(result);
    }
}
