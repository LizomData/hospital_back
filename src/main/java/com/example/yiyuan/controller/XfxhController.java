package com.example.yiyuan.controller;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.service.XfxhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@Api(tags = "AI聊天控制器")
@RequestMapping("/ai")
public class XfxhController extends ABaseController{
    private static final Logger logger = LoggerFactory.getLogger(XfxhController.class);

    @Resource
    private XfxhService xfxhService;

    @PostMapping("/chat")
    @ApiOperation(value = "发消息给ai", notes = "需要从请求头中传递 token 以验证用户身份")
    public String chat(@ApiIgnore HttpServletRequest request,
                       @ApiParam(name = "content", value = "消息内容", required = true) @NotNull @RequestParam String content) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        return xfxhService.chat(tokenUserInfoDto.getUserId(), content);
    }
}
