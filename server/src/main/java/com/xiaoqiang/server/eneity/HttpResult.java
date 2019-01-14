package com.xiaoqiang.server.eneity;

public class HttpResult {
    private boolean result;

    public HttpResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
