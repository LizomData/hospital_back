package com.example.yiyuan.controller;

import com.example.yiyuan.entity.dto.StatResultDTO;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.query.UserOutlineApplyQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.entity.vo.SymptomCountVO;
import com.example.yiyuan.service.UserContactApplyService;
import com.example.yiyuan.service.UserInfoService;
import com.example.yiyuan.service.UserOutlineApplyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/myPatient")
public class MyPatientController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(MyPatientController.class);
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserContactApplyService userContactApplyService;
    @Resource
    private UserOutlineApplyService userOutlineApplyService;

    @ApiOperation(value = "获取我的患者总数", notes = "需要从请求头中传递 token 以验证用户身份")
    @GetMapping("/selectMyPatient")
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
        UserOutlineApplyQuery userOutlineApplyQuery = new UserOutlineApplyQuery();
        userOutlineApplyQuery.setReceiveUserId(userId);
        return getSuccessResponseVO(userOutlineApplyService.findCountByParam(userOutlineApplyQuery));
    }

    @GetMapping("/getBySex")
    public ResponseVO getBySex(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }


        return getSuccessResponseVO(userInfoService.getBySex(userId));
    }

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
        return getSuccessResponseVO(userInfoService.findCount(userId, type));
    }

    @ApiOperation(value = "查询出院以及就诊人数", notes = "需要从请求头中传递 token 以验证用户身份")
    @GetMapping("/symptomCount")
    public ResponseVO symptomCount(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }
        List<SymptomCountVO> result = userOutlineApplyService.countSymptomsByUserId(userId);

        return getSuccessResponseVO(result);
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
        return getSuccessResponseVO(userInfoService.byAge(userId));
    }

    //获取我的患者详细信息
    @GetMapping("/getDetail")
    public ResponseVO getDetail(@ApiIgnore HttpServletRequest request, Integer pageNum) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        String firstLetter = String.valueOf(userId.charAt(0));
        if (firstLetter.equals("U")) {
            return getErrorResponseVO("您无权限查看");
        }
        if (pageNum == null || pageNum == ' ') {
            pageNum = 1;
        }
        pageNum = (pageNum - 1) * 10;
        return getSuccessResponseVO(userInfoService.getDetail(userId, pageNum));
    }

    @GetMapping("/daily")
    public ResponseVO getDailyStats(HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }

        String userId = tokenUserInfoDto.getUserId();

        List<String> dateList = Arrays.asList(
                "2025-03-30", "2025-03-31", "2025-04-01",
                "2025-04-02", "2025-04-03", "2025-04-04",
                "2025-04-05", "2025-04-06", "2025-04-07",
                "2025-04-08", "2025-04-09", "2025-04-10",
                "2025-04-11", "2025-04-12", "2025-04-13"
        );

        List<Integer> appointmentList = Arrays.asList(
                0, 1, 1, 2, 2, 2,
                1, 0, 1, 0, 1, 1,
                0, 0, 1
        );

        List<Integer> patientList = Arrays.asList(
                0, 1, 2, 2, 3, 2,
                1, 0, 2, 1, 1, 0,
                0, 0, 1
        );
        StatResultDTO statResult = new StatResultDTO(dateList, appointmentList, patientList);
        return getSuccessResponseVO(statResult);
//        return getSuccessResponseVO(userInfoService.getDailyStatistics(userId));
    }
}
