package com.xiaoqiang.client.configuration;

import com.xiaoqiang.client.controller.MonitorController;
import com.xiaoqiang.client.postprocessor.XiaoQiangRetryBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(XiaoQiangMarkerConfiguration.Marker.class)
public class XiaoQiangAutoConfiguration {

//    private ConfigurableEnvironment env;
//    //RelaxedPropertyResolver在2.0版本的SpringBoot中不存在
//    private RelaxedPropertyResolver propertyResolver;

    /**
     * 注入bean类的方式
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.monitor", name = "enabled", matchIfMissing = true)
    public MonitorController monitorController() {
        return new MonitorController();
    }

    @Configuration
    protected static class XiaoQiangConfigBeanConfiguration {

        @Bean
        @ConditionalOnProperty(prefix = "xiaoqiang.retry", name = "enabled", matchIfMissing = true)
        public XiaoQiangRetryBeanPostProcessor xiaoQiangBeanPostProcessor(){
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
