package com.xiaoqiang.client.postprocessor;

import com.xiaoqiang.client.annotation.XiaoQiangRetry;
import com.xiaoqiang.client.configuration.XiaoQiangRetryConfigBean;
import com.xiaoqiang.client.retry.CglibEnhencer;
import com.xiaoqiang.client.retry.JDKEnhencer;
import com.xiaoqiang.client.retry.XiaoQiangMethodRetryInfo;
import com.xiaoqiang.client.retry.XiaoQiangRetryInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class XiaoQiangRetryBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private XiaoQiangRetryConfigBean xiaoQiangRetryConfigBean;

    /**
     * 参数加载：控制台上的参数>注解上的参数>yaml上配置的参数
     * 如果类上增加@XiaoQiangRetry，那么该类下的所有方法都会错误重试
     * 如果方法上增加@XiaoQiangRetry，那么该方法会错误重试
     *
     * @return 无论是包装后的bean还是初始bean，都要返回一个，自定义的BeanPostProcessor不要返回空。
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        //0.0初始化资源
        XiaoQiangRetryInfo xiaoQiangRetryInfo = null;
        boolean isClassAnnotation = false;
        boolean isMethodAnnotation = false;
        Map<String, XiaoQiangMethodRetryInfo> methodRetryInfos = null;

        //1.0解读类上的注解。
        /**
         *  getDeclaredAnnotations方法返回当前类直接引用的注解
         *  getAnnotatedInterfaces()方法返回的是当前类实现的接口信息
         */
        Annotation[] annotations = bean.getClass().getDeclaredAnnotations();

        //1.1遍历类的所有注解
        for (Annotation declaredAnnotation : annotations) {
            //注解的名字不要拿错了
            String typeName = declaredAnnotation.annotationType().getName();
            //1.2如果类上有XiaoQiangRetry注解，那么将isAnnotation置为true
            if (typeName.equals("com.xiaoqiang.client.annotation.XiaoQiangRetry")) {
                System.out.println("**********类" + beanName + "中包含XiaoQiangRetry注解了");
                isClassAnnotation = true;

                //1.3读取注解中的配置信息，并封装到xiaoQiangRetryInfo中
                XiaoQiangRetry annotation = (XiaoQiangRetry) declaredAnnotation;
                xiaoQiangRetryInfo = new XiaoQiangRetryInfo(prepareRetryCount(annotation), prepareRetryDelay(annotation), isClassAnnotation);

                //如果检测到类注解，那么直接结束此循环
                break;
            }
        }

        //2.0如果类上没有XiaoQiangRetry注解，则遍历方法
        if (!isClassAnnotation) {
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            //2.1遍历方法
            for (Method declaredMethod : declaredMethods) {
                //每个方法对应的属性信息不同，要清空信息
                methodRetryInfos = null;
                String methodName = declaredMethod.getName();
                Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    String name = declaredAnnotation.annotationType().getName();
                    if (name.equals("com.xiaoqiang.client.annotation.XiaoQiangRetry")) {
                        //2.2读取注解中的信息，并放入XiaoQiangRetryInfo中
                        // 如果注解中没有配置参数，则使用yaml配置文件中的参数
                        XiaoQiangRetry annotation = (XiaoQiangRetry) declaredAnnotation;

                        XiaoQiangMethodRetryInfo methodRetryInfo = new XiaoQiangMethodRetryInfo(prepareRetryCount(annotation),prepareRetryDelay(annotation));
                        if (methodRetryInfos == null) {
                            methodRetryInfos = new HashMap<>();
                        }
                        methodRetryInfos.put(methodName, methodRetryInfo);
                        isMethodAnnotation = true;

                        System.out.println("**********方法" + methodName + "中包含XiaoQiangRetry注解了");
                    }
                }
            }
            //如果注解中未注明参数，那么从yaml配置中读取
            // 如果yaml配置中也未注明参数，则读取注解的默认值
            // 后期会从Server端读取参数
            xiaoQiangRetryInfo = new XiaoQiangRetryInfo(methodRetryInfos);
        }

        //3.0将注解信息封装到XiaoQiangRetryInfo中传递给代理类
        //增强该方法，实际上也是给这个类创建代理，不过只增强特定的方法
        return (isClassAnnotation || isMethodAnnotation) ? enhenceBean(bean, xiaoQiangRetryInfo) : bean;
    }

    //检查注解是否配置属性，如果注解配置，那么将注解的属性值存入；
    // 如果未配置注解属性，则从配置文件中读取配置。

    private int prepareRetryCount(XiaoQiangRetry annotation) {
        //注解默认配置，全部为0
        int retryCount = 0;

        //检查注解的值
        if (annotation.retryCount() == 0) {
            retryCount = xiaoQiangRetryConfigBean.getRetryCount();
        } else {
            retryCount = annotation.retryCount();
        }
        return retryCount;
    }

    private int prepareRetryDelay(XiaoQiangRetry annotation) {
        //注解默认配置，全部为0
        int retryDelay = 0;

        //检查注解的值
        if (annotation.retryDelay() == 0) {
            retryDelay = xiaoQiangRetryConfigBean.getRetryDelay();
        } else {
            retryDelay = annotation.retryDelay();
        }
        return retryDelay;
    }

    /**
     * 增强bean中的所有方法，正常业务逻辑处理类，不会包含get/set方法、object中的方法
     *
     * @param bean 传入的原始bean
     * @return 增强后的bean
     */
    private Object enhenceBean(Object bean, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        if ("cglib".equals(xiaoQiangRetryConfigBean.getProxyWay())) {
            Object instance = new CglibEnhencer(xiaoQiangRetryInfo).getInstance(bean);
            return instance;
        } else {
            JDKEnhencer handler = new JDKEnhencer(bean, xiaoQiangRetryInfo);
            Object instance = Proxy.newProxyInstance(handler.getClass().getClassLoader(), bean.getClass().getInterfaces(), handler);
            return instance;
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
