package com.xiaoq.client;


import com.xiaoqiang.client.annotation.EnableXiaoQiangClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableXiaoQiangClient
public class ClientDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class);
    }
}
