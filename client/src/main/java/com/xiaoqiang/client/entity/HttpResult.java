package com.xiaoqiang.client.entity;

public class HttpResult {
    private boolean result;

    public HttpResult(boolean result) {
        this.result = result;
    }

    public HttpResult() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
