package org.example.cloudchat.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.cloudchat.config.WebSocketChannelConfig;
import org.example.cloudchat.entity.Netty;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Slf4j
@Component
public class NettyWebSocketServer implements Runnable{
    @Resource
    private Netty netty;

    @Resource
    private WebSocketChannelConfig webSocketChannelConfig;

    /**
     * boss线程组，用于接收客户端传来的连接请求
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /**
     * worker线程组，处理boss线程组传过来的请求
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**
     * 资源关闭——在容器销毁时关闭
     */
    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


    @Override
    public void run() {
        try {
            /**
             * serverBootstrap：服务端启动助手，
             * 用来为netty程序的启动组装配置一些必要组件，例如那两个线程组
             */
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(webSocketChannelConfig);
            // 启动
            ChannelFuture channelFuture = serverBootstrap.bind(netty.getPort()).sync();
            log.info("【-----Netty服务端启动成功-----】");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
