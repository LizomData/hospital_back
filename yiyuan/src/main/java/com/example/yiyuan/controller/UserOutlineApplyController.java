package com.example.yiyuan.controller;

import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.ResponseCodeEnum;
import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.po.UserOutlineApply;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.entity.query.UserOutlineApplyQuery;
import com.example.yiyuan.entity.vo.ApplyOnlineVo;
import com.example.yiyuan.entity.vo.OutlineApplyVO;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.UserOutlineApplyMapper;
import com.example.yiyuan.service.UserOutlineApplyService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Description: Controller *
 * @author:??
 * @date:2025/03/26
 */
@RestController
@Api("线下预约医生")
@RequestMapping("/outlineApply")
public class UserOutlineApplyController extends ABaseController {

    Logger logger = LoggerFactory.getLogger(UserOutlineApplyController.class);
    @Resource
    private UserOutlineApplyService userOutlineApplyService;
    @Resource
    private UserOutlineApplyMapper<UserOutlineApply, UserOutlineApplyQuery> userOutlineApplyMapper;

    //加载线下请求
    @GetMapping("/loadOutlineApply")
    @ApiOperation(value = "加载线下预约自己的所有病人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO loadOnlineApply(@ApiIgnore HttpServletRequest request,
                                      @ApiParam(name = "status", value = "状态") Integer status,
                                      @ApiParam(name = "keyword", value = "搜索姓名或者症状") String keyword) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto == null) {
            return guoqi(null);
        }
        UserOutlineApplyQuery userContactApplyQuery = new UserOutlineApplyQuery();
        if (keyword != null) {
            userContactApplyQuery.setNickName(keyword);
        }

        if (status != null) {
            userContactApplyQuery.setUserStatus(status);
        }

        userContactApplyQuery.setOrderBy("FIELD(status,1,0,2), last_apply_time desc");
        userContactApplyQuery.setReceiveUserId(tokenUserInfoDto.getUserId());

        List<OutlineApplyVO> result = userOutlineApplyService.loadOnlineApply(userContactApplyQuery);

        return getSuccessResponseVO(result);
    }


    @ApiOperation(value = "线下预约医生", notes = "需要从请求头中传递 token 以验证用户身份")
    @PostMapping("/applyAdd")
    public ResponseVO applyAdd(@ApiIgnore HttpServletRequest request,
                               @ApiParam(name = "userInfo", value = "用户修改的信息", required = true) @NotNull UserOutlineApply userOutlineApply
    ) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto == null) {
            return guoqi(null);
        }
        userOutlineApply.setApplyUserId(tokenUserInfoDto.getUserId());
        Date curDate = new Date();
        userOutlineApply.setLastApplyTime(curDate);
        userOutlineApply.setStatus(0);

        UserOutlineApply userOutlineApply1 = userOutlineApplyMapper.selectByApplyUserIdAndReceiveUserId(tokenUserInfoDto.getUserId(), userOutlineApply.getReceiveUserId());
        if (userOutlineApply1 == null) {
            userOutlineApplyService.add(userOutlineApply);

        } else {
            userOutlineApplyService.updateUserOutlineApplyByApplyId(userOutlineApply, userOutlineApply1.getApplyId());
        }


        return getSuccessResponseVO(userOutlineApply1.getApplyId());
    }

    @PostMapping("/alterStatus")
    public ResponseVO alterStatus(@ApiIgnore HttpServletRequest request, @NotNull Integer applyId, @NotNull Integer status
    ) {
        UserOutlineApply userOutlineApply = new UserOutlineApply();
        userOutlineApply.setStatus(status);
        userOutlineApplyService.updateUserOutlineApplyByApplyId(userOutlineApply, applyId);
        return getSuccessResponseVO(null);
    }

    @PostMapping("/uploadFile")
    @GlobalInterceptor
    @ApiOperation(value = "上传附件，支持上传多个文件", notes = "需要从请求头中传递 token 以验证用户身份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "文件列表", dataType = "__file", allowMultiple = true, paramType = "form")
    })
    public ResponseVO uploadFile(
            @ApiIgnore HttpServletRequest request,
            @ApiParam(name = "applyId", value = "预约的消息id", required = true) @NotNull Integer applyId,
            @ApiParam(name = "file", value = "文件个数", required = true) @NotNull String file,
            @ApiParam(name = "files", value = "文件列表", required = true) @NotNull MultipartFile[] files
    ) throws BusinessException {

        TokenUserInfoDto userInfoDto = getTokenUserInfo(request);
        logger.info("userId:" + userInfoDto.getUserId());
        if (files == null || files.length == 0) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (applyId == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        for (int i = 0; i < files.length; i++) {
            logger.info("i:" + i);
            logger.info("userId: {}, i: {}, applyId: {}", userInfoDto.getUserId(), i, applyId);

            MultipartFile fileo = files[i];
            userOutlineApplyService.saveApplyFile(userInfoDto.getUserId(), i, applyId, fileo);
        }


        return getSuccessResponseVO(null);
    }


}