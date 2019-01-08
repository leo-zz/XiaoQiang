package com.xiaoqiang.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xiaoqiang.server")
public class XiaoQiangServerProperties {


    private String path = "/";

    private boolean enabled = true;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
