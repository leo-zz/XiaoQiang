package com.xiaoqiang.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  Spring在以@Bean的方式创建对象（执行构造方法）后会自动注入属性
 *  注：如果属性的getter/setter方法名称有误，会导致属性注入失败。
 */
@ConfigurationProperties(XiaoQiangConfigBean.PREFIX)
public class XiaoQiangConfigBean {
    static final String PREFIX = "xiaoqiang";
    private String[]  xiaoQiangURLs=null;
    private String  xiaoQiangServerURL;
    private String instanceName;


    public String[] getXiaoQiangURLs() {
        if(xiaoQiangURLs==null){
            xiaoQiangURLs=this.xiaoQiangServerURL.trim().split(",");
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

    public String getXiaoQiangServerURL() {
        return xiaoQiangServerURL;
    }

    public void setXiaoQiangServerURL(String xiaoQiangServerURL) {
        this.xiaoQiangServerURL = xiaoQiangServerURL;
    }
}
