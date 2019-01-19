package com.xiaoqiang.server.configuration;

import com.xiaoqiang.server.controller.ConnController;
import com.xiaoqiang.server.controller.IndexController;
import com.xiaoqiang.server.controller.XiaoQiangController;
import com.xiaoqiang.server.properties.XiaoQiangServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnBean(XiaoQiangServerMarkerConfiguration.Marker.class)
//区别于Client端，Serer端使用@EnableConfigurationProperties注明属性类。
@EnableConfigurationProperties({XiaoQiangServerProperties.class})
public class XiaoQiangServerAutoConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.server", name = "enabled", matchIfMissing = true)
    public XiaoQiangController xiaoQiangController() {
        return new XiaoQiangController();
    }

    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.server", name = "enabled", matchIfMissing = true)
    public IndexController indexController() {
        return new IndexController();
    }

    @Bean
    @ConditionalOnProperty(prefix = "xiaoqiang.server", name = "enabled", matchIfMissing = true)
    public ConnController connController() {
        return new ConnController();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/**"}).addResourceLocations(new String[]{"classpath:/xiaoqiang-ui/"}).setCachePeriod((int) TimeUnit.DAYS.toSeconds(365L));
    }

    //允许跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

//    /**
//     * Eureka 使用Jersey，此处使用mvc的视图解析器
//     * 未生效
//     *
//     * @return
//     */
//    @Bean
//    public ViewResolver getViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("classpath:xiaoqiang-ui/");
//        resolver.setSuffix(".html");
//        resolver.setOrder(1);
//        return resolver;
//    }

//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

}
