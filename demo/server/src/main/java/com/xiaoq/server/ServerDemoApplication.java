package com.xiaoq.server;


import com.xiaoqiang.server.annotation.EnableXiaoQiangServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableXiaoQiangServer
public class ServerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerDemoApplication.class);
    }
}
