package com.xiaoqiang.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XiaoQiangRetry {

    /**
     * 默认的重试次数
     * @return
     */
    int retryCount() default 5;

    /**
     * 默认的重试时间间隔
     * @return
     */
    int retryDelay() default 200;
}
