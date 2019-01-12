package com.xiaoqiang.client.retry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk自带方式实现动态代理，非线程安全
 */
public class JDKEnhencer extends XQEnhencer implements InvocationHandler {


    public JDKEnhencer(Object subject, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        super(xiaoQiangRetryInfo);
    }

    //出现异常时重复执行5次。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.enhence(method, args, xiaoQiangRetryInfo);
    }
}