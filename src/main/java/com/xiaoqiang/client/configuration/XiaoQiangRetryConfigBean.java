package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(XiaoQiangRetryConfigBean.PREFIX)
public class XiaoQiangRetryConfigBean {
    static final String PREFIX = "xiaoqiang.retry";


    private String proxyWay="cglib";
    private int retryCount=5;
    private int retryDelay=200;
    private boolean onlyMyPackage=false;
    private String myPackagePrefix;

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

    public String getMyPackagePrefix() {
        return myPackagePrefix;
    }

    public void setMyPackagePrefix(String myPackagePrefix) {
        this.myPackagePrefix = myPackagePrefix;
    }
}
