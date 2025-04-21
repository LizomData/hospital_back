package com.example.yiyuan.websocket.netty;

import com.example.yiyuan.entity.dto.TokenUserInfoDto;
import com.example.yiyuan.entity.po.UserInfo;
import com.example.yiyuan.entity.query.UserInfoQuery;
import com.example.yiyuan.mappers.UserInfoMapper;
import com.example.yiyuan.redis.RedisComponent;
import com.example.yiyuan.utils.StringTools;
import com.example.yiyuan.websocket.ChannelContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class HandlerWebSocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static HandlerWebSocket handlerWebSocket;
    private static final Logger logger = LoggerFactory.getLogger(HandlerWebSocket.class);
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private ChannelContextUtils channelContextUtils;
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    public HandlerWebSocket() {

    }

    @PostConstruct
    public void init() {
        handlerWebSocket = this;
        handlerWebSocket.redisComponent = this.redisComponent;
        handlerWebSocket.channelContextUtils = this.channelContextUtils;
        handlerWebSocket.userInfoMapper = this.userInfoMapper;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有新的连接加入...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有连接断开...");
        handlerWebSocket.channelContextUtils.removeContext(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel channel = channelHandlerContext.channel();
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attribute.get();
/*
        logger.info("收到userId {}的消息:{}", userId, textWebSocketFrame.text());
*/
        handlerWebSocket.redisComponent.saveHeartBeat(userId);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String url = complete.requestUri();
            String token = getToken(url);
            if (token == null) {
                ctx.channel().close();
                return;
            }

            TokenUserInfoDto tokenUserInfoDto = handlerWebSocket.redisComponent.getTokenUserInfoDto(token);
            if (tokenUserInfoDto == null) {
                ctx.channel().close();
                return;
            }

            handlerWebSocket.channelContextUtils.addContext(tokenUserInfoDto.getUserId(), ctx.channel());
        }
    }

    private String getToken(String url) {
        if (StringTools.isEmpty(url) || url.indexOf("?") == -1) {
            return null;
        }
        String[] queryParams = url.split("\\?");
        if (queryParams.length != 2) {
            return null;
        }
        String[] params = queryParams[1].split("=");
        if (params.length != 2) {
            return null;
        }
        return params[1];
    }
}
