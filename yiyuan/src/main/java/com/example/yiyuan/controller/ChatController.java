package com.example.yiyuan.controller;

import com.example.yiyuan.annotation.GlobalInterceptor;
import com.example.yiyuan.entity.config.Appconfig;
import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.MessageSendDto;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.MessageTypeEnum;
import com.example.yiyuan.entity.enums.ResponseCodeEnum;
import com.example.yiyuan.entity.po.ChatMessage;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.exception.BusinessException;
import com.example.yiyuan.service.ChatMessageService;
import com.example.yiyuan.service.ChatSessionUserService;
import com.example.yiyuan.utils.StringTools;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/chat")
@Api(tags = "发送消息控制器")
public class ChatController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private Appconfig appconfig;

    @PostMapping("/sendMessage")
    @GlobalInterceptor
    @ApiOperation(value = "发送消息", notes = "需要从请求头中传递 token 以验证用户身份")
    public ResponseVO sendMessage(
            @ApiIgnore HttpServletRequest request,
            @ApiParam(name = "contactId", value = "接收人的id", required = true) @NotEmpty String contactId,
            @ApiParam(name = "messageContent", value = "消息内容", required = true, allowableValues = "range[1,500]") @NotEmpty @Max(500) String messageContent,
            @ApiParam(name = "messageType", value = "消息类型(2为普通聊天消息，4为媒体文件)", required = true) @NotEmpty Integer messageType,
            @ApiParam(name = "fileSize", value = "文件大小") Long fileSize,
            @ApiParam(name = "fileName", value = "文件名字") String fileName,
            @ApiParam(name = "fileType", value = "文件类型") Integer fileType
    ) throws BusinessException {

        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContactId(contactId);
        chatMessage.setMessageContent(messageContent);
        chatMessage.setFileSize(fileSize);
        chatMessage.setFileName(fileName);
        chatMessage.setFileType(fileType);
        chatMessage.setMessageType(messageType);
        MessageSendDto messageSendDto = chatMessageService.saveMessage(chatMessage, tokenUserInfoDto);
        return getSuccessResponseVO(messageSendDto);
    }

    @PostMapping("/uploadFile")
    @GlobalInterceptor
    @ApiOperation(value = "上传文件", notes = "需要从请求头中传递 token 以验证用户身份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "__file", paramType = "form"),
    })
    public ResponseVO uploadFile(
            @ApiIgnore HttpServletRequest request,
            @ApiParam(name = "messageId", value = "消息id", required = true) @NotNull Long messageId,
            @ApiParam(name = "file", value = "文件", required = true) @NotNull MultipartFile file
    ) throws BusinessException {

        TokenUserInfoDto userInfoDto = getTokenUserInfo(request);
        chatMessageService.saveMessageFile(userInfoDto.getUserId(), messageId, file);
        return getSuccessResponseVO(null);
    }

//    @PostMapping("/downloadFile")
//    @GlobalInterceptor
//    @ApiOperation(value = "从服务器下载文件", notes = "需要从请求头中传递 token 以验证用户身份")
//    public ResponseVO downloadFile(
//            @ApiIgnore HttpServletRequest request,
//            @ApiIgnore HttpServletResponse response,
//            @ApiParam(name = "fileId", value = "文件id", required = true) @NotEmpty String fileId,
//            @ApiParam(name = "showCover", value = "1为展示封面，0为不展示封面", required = true) @NotNull Boolean showCover
//    ) throws BusinessException {
//
//        TokenUserInfoDto userInfoDto = getTokenUserInfo(request);
//        OutputStream out = null;
//        FileInputStream in = null;
//        try {
//            File file = null;
//            if (!StringTools.isNumber(fileId)) {
//                String avatarFolderName = Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_AVATAR_NAME;
//                String avatarPath = appconfig.getProjectFolder() + avatarFolderName + fileId + Constants.IMAGE_SUFFIX;
//                if (showCover) {
//                    avatarPath = avatarPath + Constants.COVER_IMAGE_SUFFIX;
//                }
//                logger.info("avatarPath:" + avatarPath);
//                file = new File(avatarPath);
//                if (!file.exists()) {
//                    logger.error("文件不存在");
//                }
//            } else {
//                file = chatMessageService.downloadFile(userInfoDto, Long.parseLong(fileId), showCover);
//            }
//            response.setContentType("application/x-msdownload;charset=UTF-8");
//            response.setHeader("Content-Disposition", "attachment;");
//            response.setContentLengthLong(file.length());
//            in = new FileInputStream(file);
//            byte[] byteData = new byte[1024];
//            out = response.getOutputStream();
//            int len;
//            while ((len = in.read(byteData)) != -1) {
//                out.write(byteData, 0, len);
//            }
//            out.flush();
//            return getSuccessResponseVO("下载文件成功");
//        } catch (Exception e) {
//            logger.error("下载文件失败", e);
//            return getErrorResponseVO("下载文件失败");
//
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (Exception e) {
//                    logger.error("Io异常", e);
//                    return getErrorResponseVO("IO异常");
//
//                }
//            }
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (Exception e) {
//                    logger.error("Io异常", e);
//                    return getErrorResponseVO("IO异常");
//
//                }
//            }
//
//        }
//
//    }
}
