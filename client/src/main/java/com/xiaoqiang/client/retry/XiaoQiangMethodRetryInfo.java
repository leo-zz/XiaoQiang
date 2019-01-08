package com.xiaoqiang.client.retry;

import java.util.List;

public class XiaoQiangMethodRetryInfo {


    private int retryCount;
    private int retryDelay;

    public XiaoQiangMethodRetryInfo(int retryCount, int retryDelay) {
        this.retryCount = retryCount;
        this.retryDelay = retryDelay;
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
}