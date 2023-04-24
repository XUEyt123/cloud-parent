package org.example.cloudchat.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static List<Channel> channelList = new ArrayList<>();


    /**
     * 通道活性
     *
     * @param ctx ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //当有新的客户端连接的时候, 将通道放入集合
        channelList.add(channel);
        log.info("有新的连接加入");
    }

    /**
     * 频道不活跃
     *
     * @param ctx ctx
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelList.remove(channel);
        log.info("有连接已经断开。。。。。");
    }

    /**
     * 通道read0
     *
     * @param ctx                ctx
     * @param textWebSocketFrame 文本框架网络套接字
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 获取服务器下发的消息
        String msg = textWebSocketFrame.text();
        log.info("msg:{}", msg);
        //当前发送消息的通道, 当前发送的客户端连接
        Channel channel = ctx.channel();
        for (Channel channel1 : channelList) {
            // 排除自身通道
            if (channel != channel1) {
                // 通过管道给其他客户端发送消息
                channel1.writeAndFlush(new TextWebSocketFrame(msg));
            }
        }
    }

    /**
     * 例外了
     *
     * @param ctx   ctx
     * @param cause 导致
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        // 移除集合
        channelList.remove(channel);
    }
}
