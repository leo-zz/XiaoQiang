package com.xiaoqiang.client.configuration;

import com.xiaoqiang.client.controller.ExceptionController;
import com.xiaoqiang.client.controller.MonitorController;
import com.xiaoqiang.client.postprocessor.XiaoQiangRetryBeanPostProcessor;
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

//    private ConfigurableEnvironment env;
//    //RelaxedPropertyResolver在2.0版本的SpringBoot中不存在
//    private RelaxedPropertyResolver propertyResolver;

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
