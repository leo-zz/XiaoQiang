package com.xiaoqiang.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 只要主函数上标记有@EnableXiaoQiangClient，就会在容器中加载个Marker类
 */
@Configuration
public class XiaoQiangServerMarkerConfiguration {

    @Bean
    public Marker xiaoQiangServerMarkerBean() {
        return new Marker();
    }

    class Marker {
    }
}
