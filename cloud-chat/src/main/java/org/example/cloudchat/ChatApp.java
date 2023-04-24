package org.example.cloudchat;

import org.example.cloudchat.server.NettyWebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@SpringBootApplication
public class ChatApp implements CommandLineRunner {

    @Resource
    NettyWebSocketServer server;

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        // 在新的线程中启动Netty服务器
        new Thread(server).start();

    }
}
