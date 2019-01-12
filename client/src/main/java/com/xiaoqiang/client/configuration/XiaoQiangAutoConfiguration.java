package com.xiaoqiang.client.configuration;

import com.xiaoqiang.client.controller.ExceptionController;
import com.xiaoqiang.client.controller.MonitorController;
import com.xiaoqiang.client.postprocessor.XiaoQiangRetryBeanPostProcessor;
import com.xiaoqiang.client.util.XiaoQiangHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ConditionalOnBean(XiaoQiangMarkerConfiguration.Marker.class)
public class XiaoQiangAutoConfiguration extends WebMvcConfigurerAdapter {


    //注意@Value中# 与 $的区别。
    // #表示SPEL表达式，主要就是创建一个对象，然后通过对象获取值。
    // $是占位符，表示属性值。
    @Value("${xiaoqiang.xiaoQiangURL}")
    private String  xiaoQiangURL;


    /**
     * 注入bean类的方式
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.monitor", name = "enabled", matchIfMissing = true)
    public MonitorController monitorController() {
        return new MonitorController();
    }

    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.retry", name = "enabled", matchIfMissing = true)
    public ExceptionController ExceptionController() {
        return new ExceptionController();
    }

    @Bean
    public XiaoQiangHttpClient XiaoQiangHttpClient(){
        return new XiaoQiangHttpClient(xiaoQiangURL);
    }

    //循序跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


    @Configuration
    protected static class XiaoQiangConfigBeanConfiguration {

        @Bean
        @ConditionalOnProperty(prefix = "xiaoqiang.retry", name = "enabled", matchIfMissing = true)
        public XiaoQiangRetryBeanPostProcessor xiaoQiangBeanPostProcessor() {
            return new XiaoQiangRetryBeanPostProcessor();
        }

        @Bean
        @ConditionalOnMissingBean
        public XiaoQiangMonitorConfigBean xiaoQiangMonitorConfigBean() {
            return new XiaoQiangMonitorConfigBean();
        }

        @Bean
        @ConditionalOnMissingBean
        public XiaoQiangRetryConfigBean xiaoQiangRetryConfigBean() {
            return new XiaoQiangRetryConfigBean();
        }
    }

}
