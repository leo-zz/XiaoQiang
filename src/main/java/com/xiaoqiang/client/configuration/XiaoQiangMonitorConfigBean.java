package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(XiaoQiangMonitorConfigBean.PREFIX)
public class XiaoQiangMonitorConfigBean {

    static final String PREFIX = "xiaoqiang.monitor";

    private boolean classloader = false;
    private boolean cpu = false;
    private boolean gc = false;
    private boolean  memory = false;
    private boolean runtime = false;
    private boolean thread = false;

    public static String getPREFIX() {
        return PREFIX;
    }

    public boolean isClassloader() {
        return classloader;
    }

    public void setClassloader(boolean classloader) {
        this.classloader = classloader;
    }

    public boolean isCpu() {
        return cpu;
    }

    public void setCpu(boolean cpu) {
        this.cpu = cpu;
    }

    public boolean isGc() {
        return gc;
    }

    public void setGc(boolean gc) {
        this.gc = gc;
    }

    public boolean isMemory() {
        return memory;
    }

    public void setMemory(boolean memory) {
        this.memory = memory;
    }

    public boolean isRuntime() {
        return runtime;
    }

    public void setRuntime(boolean runtime) {
        this.runtime = runtime;
    }

    public boolean isThread() {
        return thread;
    }

    public void setThread(boolean thread) {
        this.thread = thread;
    }
}
