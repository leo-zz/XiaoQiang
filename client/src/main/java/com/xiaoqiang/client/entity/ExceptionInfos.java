package com.xiaoqiang.client.entity;

import java.util.Date;
import java.util.List;

public class ExceptionInfos {
    String threadName;
    String methodName;
    Date exTime;
    long spendTime;
    boolean result;
    int retryCount;
    List<ExceptionInfoEntity> exceptionInfoEntities;
    //方法名

    public ExceptionInfos(String methodName,Long timeStramp,long spendTime,boolean result, int retryCount, List<ExceptionInfoEntity> exceptionInfoEntities) {
        this.threadName = Thread.currentThread().getName();
        this.methodName = methodName;
        this.exTime=new Date(timeStramp);
        this.spendTime=spendTime;
        this.result = result;
        this.retryCount = retryCount;
        this.exceptionInfoEntities = exceptionInfoEntities;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public List<ExceptionInfoEntity> getExceptionInfoEntities() {
        return exceptionInfoEntities;
    }

    public void setExceptionInfoEntities(List<ExceptionInfoEntity> exceptionInfoEntities) {
        this.exceptionInfoEntities = exceptionInfoEntities;
    }

    public Date getExTime() {
        return exTime;
    }

    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }
}
