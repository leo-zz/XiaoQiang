package com.xiaoqiang.client.configuration;

import com.xiaoqiang.client.controller.ExceptionController;
import com.xiaoqiang.client.controller.MonitorController;
import com.xiaoqiang.client.postprocessor.XiaoQiangRetryBeanPostProcessor;
import com.xiaoqiang.client.util.XiaoQiangHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * XiaoQiang的自动配置类。
 *
 * @Author:leo-zz
 */
@Configuration
@ConditionalOnBean(XiaoQiangMarkerConfiguration.Marker.class)
public class XiaoQiangAutoConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 注入XiaoQiang的JVM信息监控controller，默认自动开启
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.monitor", name = "enabled", matchIfMissing = true)
    public MonitorController monitorController() {
        return new MonitorController();
    }

    /**
     * 注入XiaoQiang的方法重试监控controller，默认自动开启
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.retry", name = "enabled", matchIfMissing = true)
    public ExceptionController ExceptionController() {
        return new ExceptionController();
    }

    /**
     * 注入向Server端注册、发送心跳、取消注册的bean类
     *
     * @return
     */
    @Bean
    public XiaoQiangHttpClient XiaoQiangHttpClient() {
        return new XiaoQiangHttpClient();
    }

    /**
     * SpringWeb提供的允许跨域访问的配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


    /**
     * 若开启方法错误重试功能，会将XiaoQiang的后置处理器注入的容器中。
     * 在refresh()过程中检查所有注有@XiaoQiangRetry的类或方法，使用动态代理实现错误重试的逻辑。
     */
    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.retry", name = "enabled", matchIfMissing = true)
    public XiaoQiangRetryBeanPostProcessor xiaoQiangBeanPostProcessor() {
        return new XiaoQiangRetryBeanPostProcessor();
    }

    /**
     * 注入XiaoQiangMonitorConfigBean，通过配置选择JVM监控的内容。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public XiaoQiangMonitorConfigBean xiaoQiangMonitorConfigBean() {
        return new XiaoQiangMonitorConfigBean();
    }

    /**
     *
     * 注入XiaoQiangRetryConfigBean，通过配置设定方法错误重试的参数。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public XiaoQiangRetryConfigBean xiaoQiangRetryConfigBean() {
        return new XiaoQiangRetryConfigBean();
    }

    /**
     *  注入{@link XiaoQiangConfigBean}，通过配置设置Server通信相关的参数。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public XiaoQiangConfigBean xiaoQiangConfigBean() {
        return new XiaoQiangConfigBean();
    }
}
