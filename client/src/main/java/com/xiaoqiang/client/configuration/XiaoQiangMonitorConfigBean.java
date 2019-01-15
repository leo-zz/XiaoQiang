package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spring根据注解和映射metadata，自动将配置文件中的属性值注入
 */
@ConfigurationProperties(XiaoQiangMonitorConfigBean.PREFIX)
public class XiaoQiangMonitorConfigBean {

    static final String PREFIX = "xiaoqiang.monitor";

    //属性名为classloader，但配置中为class.
    private boolean clazz = false;
    private boolean cpu = false;
    private boolean gc = false;
    private boolean  memory = false;
    private boolean runtime = false;
    private boolean thread = false;

    public static String getPREFIX() {
        return PREFIX;
    }

    public boolean isClazz() {
        return clazz;
    }

    public void setClazz(boolean clazz) {
        this.clazz = clazz;
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
