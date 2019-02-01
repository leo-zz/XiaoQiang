package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 *  用于解析配置文件中的数据，确定方法重试的次数、重试时间间隔、是否只打印项目中代码的异常信息、动态代理的方式。
 *  Spring根据注解和映射metadata，自动将配置文件中的属性值注入。
 *
 *  @Author:leo-zz
 */
@ConfigurationProperties(XiaoQiangRetryConfigBean.PREFIX)
public class XiaoQiangRetryConfigBean {
    static final String PREFIX = "xiaoqiang.retry";


    private String proxyWay="cglib";
    private int retryCount=5;
    private int retryDelay=200;
    private boolean onlyMyPackage;
    private static String myPackagePrefix;

    public XiaoQiangRetryConfigBean() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[stackTrace.length-1].getClassName();
        int i = className.lastIndexOf(".");
        myPackagePrefix=className.substring(0,i);
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public String getProxyWay() {
        return proxyWay;
    }

    public void setProxyWay(String proxyWay) {
        this.proxyWay = proxyWay;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(int retryDelay) {
        this.retryDelay = retryDelay;
    }

    public boolean isOnlyMyPackage() {
        return onlyMyPackage;
    }

    public void setOnlyMyPackage(boolean onlyMyPackage) {
        this.onlyMyPackage = onlyMyPackage;
    }

    public static String getMyPackagePrefix() {
        return myPackagePrefix;
    }

}
