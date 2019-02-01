package com.xiaoqiang.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 只要主函数上标记有@EnableXiaoQiangClient，就会在容器中加载个Marker类。
 * 作为开启XiaoQiangClient的一个标识。
 *
 * @Author:leo-zz
 */
@Configuration
public class XiaoQiangMarkerConfiguration {

    @Bean
    public Marker xiaoQiangMarkerBean() {
        return new Marker();
    }

    class Marker {
    }
}
