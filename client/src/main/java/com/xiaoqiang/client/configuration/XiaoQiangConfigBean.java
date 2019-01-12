package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(XiaoQiangConfigBean.PREFIX)
public class XiaoQiangConfigBean {
    static final String PREFIX = "xiaoqiang";
    private String[]  xiaoQiangURLs;
    private String  xiaoQiangURL;

    public XiaoQiangConfigBean(String xiaoQiangURL) {
        this.xiaoQiangURL = xiaoQiangURL;
        this.xiaoQiangURLs=xiaoQiangURL.trim().split(",");
    }

    public String[] getXiaoQiangURLs() {
        return xiaoQiangURLs;
    }

    public void setXiaoQiangURLs(String[] xiaoQiangURLs) {
        this.xiaoQiangURLs = xiaoQiangURLs;
    }
}
