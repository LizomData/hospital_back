package com.example.yiyuan.controller;

import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.controller.ABaseController;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.dto.UserContactSearchResultDto;
import com.example.yiyuan.entity.enums.*;
import com.example.yiyuan.entity.po.UserContact;
import com.example.yiyuan.entity.po.UserContactApply;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.UserContactApplyQuery;
import com.example.yiyuan.entity.query.UserContactQuery;
import com.example.yiyuan.entity.vo.*;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.UserContactApplyMapper;
import com.example.yiyuan.service.UserContactApplyService;
import com.example.yiyuan.service.UserContactService;
import com.example.yiyuan.service.UserInfoService;
import com.example.yiyuan.service.impl.UserContactApplyServiceImpl;
import com.example.yiyuan.utils.CopyTools;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.List;

@RestController
@Api(tags = "聊天端控制器")
@RequestMapping("/contact")
public class UserContactController extends ABaseController {
    Logger logger = LoggerFactory.getLogger(UserContactController.class);
    @Autowired
    private UserContactService userContactService;
    @Autowired
    private UserInfoService userInfoService;

    //用于申请好友
    @Autowired
    private UserContactApplyService userContactApplyService;

    @GetMapping("/search")
    @ApiOperation(value = "根据医生id查询", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO search(@ApiIgnore HttpServletRequest request,
                             @ApiParam(name = "contactId", value = "医生的Id", required = true) @NotEmpty String contactId)
            throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContactSearchResultDto resultDto = userContactService.searchContact(tokenUserInfoDto.getUserId(), contactId);
        return getSuccessResponseVO(resultDto);
    }

    @ApiOperation(value = "预约医生", notes = "需要从请求头中传递 token 以验证用户身份")
    @PostMapping("/applyAdd")
    @GlobalInterceptor
    public ResponseVO applyAdd(@ApiIgnore HttpServletRequest request,
                               @ApiParam(name = "userContactApply", value = "用户预约信息", required = true) @NotNull UserContactApply userContactApply)
            throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        Integer joinType = userContactService.applyAdd(tokenUserInfoDto, userContactApply);
        return getSuccessResponseVO(joinType);
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
        if (userInfoDto==null){
            return guoqi(null);
        }
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
            userContactApplyService.saveApplyFile(userInfoDto.getUserId(), i, applyId, fileo);
        }


        return getSuccessResponseVO(null);
    }


    //加载所有请求
    @GetMapping("/loadApply")
    @ApiOperation(value = "加载预约自己的所有病人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO loadApply(@ApiIgnore HttpServletRequest request,
                                @ApiParam(name = "pageNo", value = "页码，从1开始", example = "1") Integer pageNo,
                                @ApiParam(name = "nickName", value = "姓名") String nickName,
                                @ApiParam(name = "sex", value = "性别") Integer sex,
                                @ApiParam(name = "status", value = "就诊状态") Integer status) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        if (nickName != null) {
            applyQuery.setNickName(nickName);
        }
        if (sex != null) {
            applyQuery.setSex(sex);
        }
        if (status != null) {
            applyQuery.setUserStatus(status);
        }
        applyQuery.setOrderBy("last_apply_time desc");
        applyQuery.setReceiveUserId(tokenUserInfoDto.getUserId());
        applyQuery.setPageNo(pageNo);
        applyQuery.setPageSize(PageSize.SIZE15.getSize());
        applyQuery.setQueryContactInfo(true);
        PaginationResultVO resultVO = userContactApplyService.findListByPage(applyQuery);

        return getSuccessResponseVO(resultVO);
    }

    //加载所有请求
    @GetMapping("/loadOnlineApply")
    @ApiOperation(value = "加载线上预约自己的所有病人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO loadOnlineApply(@ApiIgnore HttpServletRequest request,
                                @ApiParam(name = "status", value = "状态") Integer status,
                                @ApiParam(name = "type", value = "类型（图文咨询或报告解读）") String type,
                                @ApiParam(name = "keyword", value = "搜索姓名或者症状") String keyword) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        if (keyword != null) {
            applyQuery.setNickName(keyword);
        }
        if (type != null) {
            applyQuery.setType(type);
        }
        if (status != null) {
            applyQuery.setUserStatus(status);
        }
        applyQuery.setOrderBy("last_apply_time desc");
        applyQuery.setReceiveUserId(tokenUserInfoDto.getUserId());

        List<ApplyOnlineVo> result = userContactApplyService.loadOnlineApply(applyQuery);

        return getSuccessResponseVO(result);
    }

    //处理请求
    @PostMapping("/dealWithApply")
    @ApiOperation(value = "处理预约情况", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO dealWithApply(@ApiIgnore HttpServletRequest request,
                                    @ApiParam(name = "applyId", value = "申请的数据的Id", required = true) @NotNull Integer applyId,
                                    @ApiParam(name = "status", value = "如何处理这条数据", required = true, example = "1为同意，2为拒绝，3为拉黑") @NotNull Integer status) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        this.userContactApplyService.dealWithApply(tokenUserInfoDto.getUserId(), applyId, status);

        return getSuccessResponseVO(null);
    }

    @GetMapping("/loadContact")
    @ApiOperation(value = "获取联系人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO loadContact(@ApiIgnore HttpServletRequest request) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContactQuery contactQuery = new UserContactQuery();
        contactQuery.setUserId(tokenUserInfoDto.getUserId());
        contactQuery.setQueryContactUserInfo(true);
        contactQuery.setOrderBy("last_update_time desc");
        contactQuery.setStatusArray(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
        });
        List<UserContact> contactList = userContactService.findListByParam(contactQuery);

        return getSuccessResponseVO(contactList);
    }

    @GetMapping("/loadMyApply")
    @ApiOperation(value = "获取我预约的医生", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO loadMyApply(@ApiIgnore HttpServletRequest request) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        List<MyApplyVO> contactList = userContactApplyService.findList(tokenUserInfoDto.getUserId());

        return getSuccessResponseVO(contactList);
    }

    @GetMapping("/getContactInfo")
    @ApiOperation(value = "获取联系人详情(非好友也可以获取)", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO getContactInfo(@ApiIgnore HttpServletRequest request,
                                     @ApiParam(name = "contactId", value = "联系人的Id", required = true) @NotNull String contactId) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserInfo userInfo = userInfoService.getUserInfoByUserId(contactId);
        if (userInfo == null) {
            return getErrorResponseVO("请求参数错误");
        }
        UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);
        userInfoVO.setContactStatus(UserContactStatusEnum.NOT_FRIEND.getStatus());

        UserContact userContact = userContactService.getUserContactByUserIdAndContactId(tokenUserInfoDto.getUserId(), contactId);
        if (userContact != null) {
            userInfoVO.setContactStatus(UserContactStatusEnum.FRIEND.getStatus());
        }
        return getSuccessResponseVO(userInfoVO);
    }


    @GetMapping("/getContactUserInfo")
    @ApiOperation(value = "获取联系人详情(只有好友或者被删除被拉黑可以获取)", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO getContactUserInfo(@ApiIgnore HttpServletRequest request,
                                         @ApiParam(name = "contactId", value = "联系人的Id", required = true) @NotNull String contactId) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContact userContact = userContactService.getUserContactByUserIdAndContactId(tokenUserInfoDto.getUserId(), contactId);
        if (userContact == null || !ArrayUtils.contains(new Integer[]{
                UserContactStatusEnum.FRIEND.getStatus(),
                UserContactStatusEnum.DEL_BE.getStatus(),
                UserContactStatusEnum.BLACKLIST_BE.getStatus(),
        }, userContact.getStatus())) {
            return getErrorResponseVO("请求参数错误");
        }

        UserInfo userInfo = userInfoService.getUserInfoByUserId(contactId);
        UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);
        return getSuccessResponseVO(userInfoVO);
    }

    @DeleteMapping("/delContact")
    @ApiOperation(value = "删除联系人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO delContact(@ApiIgnore HttpServletRequest request,
                                 @ApiParam(name = "contactId", value = "联系人的Id", required = true) @NotNull String contactId) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        userContactService.removeUserContact(tokenUserInfoDto.getUserId(), contactId, UserContactStatusEnum.DEL);

        return getSuccessResponseVO(null);
    }

    @DeleteMapping("/BlaContact")
    @ApiOperation(value = "拉黑联系人", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO BlaContact(@ApiIgnore HttpServletRequest request,
                                 @ApiParam(name = "contactId", value = "联系人的Id", required = true) @NotNull String contactId) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        userContactService.removeUserContact(tokenUserInfoDto.getUserId(), contactId, UserContactStatusEnum.BLACKLIST);

        return getSuccessResponseVO(null);
    }

    @PostMapping("/overContact")
    @ApiOperation(value = "结束咨询会话", notes = "需要从请求头中传递 token 以验证用户身份")
    @GlobalInterceptor
    public ResponseVO overContact(@ApiIgnore HttpServletRequest request,
                                  @ApiParam(name = "contactId", value = "病人的Id", required = true) @NotNull String contactId) throws BusinessException {


        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        UserContact userContact = new UserContact();
        userContact.setStatus(UserContactStatusEnum.OVER.getStatus());
        userContactService.updateUserContactByUserIdAndContactId(userContact, tokenUserInfoDto.getUserId(), contactId);
        userContactService.updateUserContactByUserIdAndContactId(userContact, contactId, tokenUserInfoDto.getUserId());

        UserContactApply userContactApply = new UserContactApply();
        userContactApply.setStatus(UserContactApplyStatusEnum.OVER.getStatus());
        userContactApplyService.updateUserContactApplyByApplyUserIdAndReceiveUserId(userContactApply, contactId, tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(null);
    }
}
