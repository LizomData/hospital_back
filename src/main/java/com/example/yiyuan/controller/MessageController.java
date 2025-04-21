package com.example.yiyuan.controller;

import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.query.ChatMessageQuery;
import com.example.yiyuan.entity.query.ChatSessionQuery;
import com.example.yiyuan.entity.query.ChatSessionUserQuery;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.mappers.ChatSessionMapper;
import com.example.yiyuan.service.ChatMessageService;
import com.example.yiyuan.service.ChatSessionService;
import com.example.yiyuan.service.ChatSessionUserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api(tags = "聊天消息控制端")
@RequestMapping("/message")
public class MessageController extends ABaseController {

    @Resource
    private ChatMessageService chatMessageService;

    @Resource
    private ChatSessionUserService chatSessionUserService;

    @GetMapping("/getMessageList")
    @GlobalInterceptor
    @ApiOperation(value = "根据sessionId查找聊天信息", notes = "需要从请求头中传递 token 以验证用户身份")
    public ResponseVO getMessageList(
            @ApiIgnore HttpServletRequest request,
            @ApiParam(name = "sessionId", value = "聊天会话的sessionId", required = true) @NotNull String sessionId
    ) {
        ChatMessageQuery chatMessageQuery = new ChatMessageQuery();
        chatMessageQuery.setSessionId(sessionId);
        return getSuccessResponseVO(chatMessageService.findListByParam(chatMessageQuery));

    }

    @GetMapping("/getContactList")
    @GlobalInterceptor
    @ApiOperation(value = "查找联系人列表（包含sessionId,最后一条信息以及联系人名字）", notes = "需要从请求头中传递 token 以验证用户身份")
    public ResponseVO getContactList(
            @ApiIgnore HttpServletRequest request
    ) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        ChatSessionUserQuery chatSessionUserQuery = new ChatSessionUserQuery();
        chatSessionUserQuery.setUserId(tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(chatSessionUserService.findListByParam(chatSessionUserQuery));

    }

/*

    @GetMapping("/getNoReadNumber")
    @GlobalInterceptor
    @ApiOperation(value = "查找联系人列表（包含sessionId,最后一条信息以及联系人名字）", notes = "需要从请求头中传递 token 以验证用户身份")
    public ResponseVO getNoReadNumber(
            @ApiIgnore HttpServletRequest request
    ) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        ChatSessionUserQuery chatSessionUserQuery = new ChatSessionUserQuery();
        chatSessionUserQuery.setUserId(tokenUserInfoDto.getUserId());
        return getSuccessResponseVO(chatSessionUserService.findListByParam(chatSessionUserQuery));

    }
*/


}
