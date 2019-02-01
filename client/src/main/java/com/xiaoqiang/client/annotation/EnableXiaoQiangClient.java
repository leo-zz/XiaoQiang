package com.xiaoqiang.client.annotation;

import com.xiaoqiang.client.configuration.XiaoQiangMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启XiaoQiang客户端的注解。
 * @Author:leo-zz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(XiaoQiangMarkerConfiguration.class)
public @interface EnableXiaoQiangClient {

}
