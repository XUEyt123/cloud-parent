package org.example.cloudchat.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "netty")
@Data
public class Netty {
    private Integer port;
    private String path;

}
