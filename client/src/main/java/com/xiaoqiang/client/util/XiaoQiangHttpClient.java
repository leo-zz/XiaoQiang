package com.xiaoqiang.client.util;


/**
 *  将Client注册到Server端，并发送心跳，读取Server端的配置信息
 *  20190112
 */
public class XiaoQiangHttpClient {
    String[] xiaoQiangURLs;

    public XiaoQiangHttpClient(String xiaoQiangURL) {
        xiaoQiangURLs=xiaoQiangURL.trim().split(",");
    }
}
