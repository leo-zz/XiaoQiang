package com.xiaoqiang.client.retry;

import java.util.Map;

public class XiaoQiangRetryInfo {

    private int retryCount;
    private int retryDelay;
    private boolean classAnnotationed;
    private boolean methodAnnotationed;
    private Map<String,XiaoQiangMethodRetryInfo> methodsInfo;

    public XiaoQiangRetryInfo(int retryCount,int retryDelay,boolean classAnnotationed) {
        this.classAnnotationed = classAnnotationed;
        this.methodsInfo = null;
        this.methodAnnotationed = false;
        this.retryCount=retryCount;
        this.retryDelay=retryDelay;
    }

    public XiaoQiangRetryInfo(Map<String,XiaoQiangMethodRetryInfo> methodsInfo) {
        this.classAnnotationed = false;
        this.methodAnnotationed = true;
        this.methodsInfo = methodsInfo;
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

    public boolean isClassAnnotationed() {
        return classAnnotationed;
    }

    public void setClassAnnotationed(boolean classAnnotationed) {
        this.classAnnotationed = classAnnotationed;
    }

    public boolean isMethodAnnotationed() {
        return methodAnnotationed;
    }

    public void setMethodAnnotationed(boolean methodAnnotationed) {
        this.methodAnnotationed = methodAnnotationed;
    }

    public Map<String, XiaoQiangMethodRetryInfo> getMethodsInfo() {
        return methodsInfo;
    }

    public void setMethodsInfo(Map<String, XiaoQiangMethodRetryInfo> methodsInfo) {
        this.methodsInfo = methodsInfo;
    }
}