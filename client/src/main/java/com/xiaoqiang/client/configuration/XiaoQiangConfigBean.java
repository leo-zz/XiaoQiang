package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  Spring在以@Bean的方式创建对象（执行构造方法）后会自动注入属性
 */
@ConfigurationProperties(XiaoQiangConfigBean.PREFIX)
public class XiaoQiangConfigBean {
    static final String PREFIX = "xiaoqiang";
    private String[]  xiaoQiangURLs=null;
    private String  xiaoQiangURL;
    private String instanceName;


    public String[] getXiaoQiangURLs() {
        if(xiaoQiangURLs==null){
            xiaoQiangURLs=this.xiaoQiangURL.trim().split(",");
        }
        return xiaoQiangURLs;
    }

    public void setXiaoQiangURLs(String[] xiaoQiangURLs) {
        this.xiaoQiangURLs = xiaoQiangURLs;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getXiaoQiangURL() {
        return xiaoQiangURL;
    }

    public void setXiaoQiangURL(String xiaoQiangURL) {
        this.xiaoQiangURL = xiaoQiangURL;
    }
}
