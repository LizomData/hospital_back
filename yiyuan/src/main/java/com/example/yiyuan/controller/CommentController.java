package com.example.yiyuan.controller;


import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.Comment;
import com.example.yiyuan.entity.query.CommentQuery;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.entity.vo.DoctorcommentVO;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.service.CommentService;
import com.example.yiyuan.service.UserOutlineApplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController extends ABaseController {
    @Resource
    private CommentService commentService;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private UserOutlineApplyService userOutlineApplyService;

    @PostMapping("/selectComment")
    public ResponseVO selectComment(@ApiIgnore HttpServletRequest request) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();

        List<DoctorcommentVO> doctorcommentVOS = userOutlineApplyService.selectComment(userId);
        for (DoctorcommentVO doctorcommentVO : doctorcommentVOS) {
            Integer type = doctorcommentVO.getType().equals("线下就诊医生") ? 0 : 1;
            List<String> doctors = redisComponent.getCommentDoctor(userId);
            if (doctors.contains(doctorcommentVO.getDoctorId()+type)){
                doctorcommentVO.setOk(1);
            }else {
                doctorcommentVO.setOk(0);
            }
        }
        return getSuccessResponseVO(doctorcommentVOS);
    }

    @PostMapping("/comment")
    public ResponseVO comment(@ApiIgnore HttpServletRequest request, Integer type, String doctorId, String content, Integer star) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setDoctorId(doctorId);
        comment.setStar(star);
        comment.setPatientId(userId);
        comment.setType(type);
        commentService.add(comment);

        redisComponent.addCommentDoctor(userId, doctorId + type);
        return getSuccessResponseVO("评论成功");
    }
    @PostMapping("/getComment")
    public ResponseVO getComment(@ApiIgnore HttpServletRequest request, String doctorId) {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
        if (tokenUserInfoDto==null){
            return guoqi(null);
        }
        String userId = tokenUserInfoDto.getUserId();
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setDoctorId(doctorId);
        List<Comment> commentList = commentService.findListByParam(commentQuery);

        return getSuccessResponseVO(commentList);
    }

}
