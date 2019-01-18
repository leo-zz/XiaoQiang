package com.xiaoqiang.server.eneity;

public class HttpResult<T> {
    private boolean result;
    private T value;

    public HttpResult(boolean result) {
        this.result = result;
        this.value=null;
    }

    public HttpResult(boolean result, T value) {
        this.result = result;
        this.value = value;
    }

    public HttpResult() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
