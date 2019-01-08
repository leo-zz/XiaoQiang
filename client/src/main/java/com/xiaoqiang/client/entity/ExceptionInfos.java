package com.xiaoqiang.client.entity;

import java.util.List;

public class ExceptionInfos {
    String threadName;
    boolean result;
    int retryCount;
    List<ExceptionInfoEntity> exceptionInfos;

    public ExceptionInfos(boolean result, int retryCount, List<ExceptionInfoEntity> exceptionInfos) {
        this.threadName = Thread.currentThread().getName();
        this.result = result;
        this.retryCount = retryCount;
        this.exceptionInfos = exceptionInfos;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public List<ExceptionInfoEntity> getExceptionInfos() {
        return exceptionInfos;
    }

    public void setExceptionInfos(List<ExceptionInfoEntity> exceptionInfos) {
        this.exceptionInfos = exceptionInfos;
    }


}
