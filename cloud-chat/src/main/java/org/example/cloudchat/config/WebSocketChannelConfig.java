package org.example.cloudchat.config;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.example.cloudchat.entity.Netty;
import org.example.cloudchat.handler.WebSocketHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WebSocketChannelConfig extends ChannelInitializer<Channel> {

    @Resource
    private Netty netty;
    @Resource
    private WebSocketHandler webSocketHandler;


    /**
     * 初始化通道
     *
     * @param channel 通道
     * @throws Exception 异常
     */
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(8000));

        pipeline.addLast(new WebSocketServerProtocolHandler(netty.getPath()));

        pipeline.addLast(webSocketHandler);


    }
}
