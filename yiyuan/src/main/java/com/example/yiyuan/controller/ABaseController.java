package com.example.yiyuan.controller;

import com.example.yiyuan.entity.constants.Constants;
import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.enums.ResponseCodeEnum;
import com.example.yiyuan.entity.vo.ResponseVO;
import com.example.yiyuan.redis.RedisUtils;

;
;import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class ABaseController {
    @Resource
    RedisUtils redisUtils;
    protected static final String STATUS_SUCCESS="success";
    protected static final String STATUS_ERROR="error";
    protected <T> ResponseVO getSuccessResponseVO(T t){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected <T> ResponseVO getErrorResponseVO(T t){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_ERROR);
        responseVO.setCode(ResponseCodeEnum.CODE_500.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_500.getMsg());
        responseVO.setData(t);
        return responseVO;
    }
    protected <T> ResponseVO guoqi(T t){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_ERROR);
        responseVO.setCode(ResponseCodeEnum.CODE_401.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_401.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected TokenUserInfoDto getTokenUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        TokenUserInfoDto tokenUserInfoDto = (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);

        return tokenUserInfoDto;
    }
}
