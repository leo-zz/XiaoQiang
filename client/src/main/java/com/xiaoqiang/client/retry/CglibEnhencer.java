package com.xiaoqiang.client.retry;

import com.xiaoqiang.client.configuration.XiaoQiangRetryConfigBean;
import com.xiaoqiang.client.postprocessor.XiaoQiangRetryBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib方式实现动态代理，非线程安全
 */
public class CglibEnhencer extends XQEnhencer implements MethodInterceptor {

    public CglibEnhencer(XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        super(xiaoQiangRetryInfo);
    }

    public Object getInstance(Object subject) {
        super.subject = subject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(super.subject.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return super.enhence(method, args, xiaoQiangRetryInfo);
    }
}