package com.xiaoqiang.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加在方法或者类上，用于开启XiaoQiang方法重试功能。
 * 可以设置方法重试的次数和时间间隔。
 * @Author:leo-zz
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XiaoQiangRetry {

    /**
     * 默认的重试次数
     * @return
     */
    int retryCount() default 0;

    /**
     * 默认的重试时间间隔
     * @return
     */
    int retryDelay() default 0;
}
