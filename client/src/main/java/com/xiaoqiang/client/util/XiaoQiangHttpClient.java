package com.xiaoqiang.client.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoqiang.client.configuration.XiaoQiangConfigBean;
import com.xiaoqiang.client.entity.HttpResult;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将Client注册到Server端，并发送心跳，读取Server端的配置信息
 * HttpClient的操作
 * https://www.cnblogs.com/c9999/p/6636415.html
 * 20190112
 */
public class XiaoQiangHttpClient {


    @Value("${server.port:8080}")
    private int port;

    private int heartBeatInteral = 10 * 1000;
    private int registerInteral = 5 * 1000;

    @Autowired
    private XiaoQiangConfigBean xiaoQiangConfigBean;
    private List<NameValuePair> nvps = null;
    private String[] xiaoQiangURLs;
    private CloseableHttpClient httpCilent = null;
    private RequestConfig requestConfig = null;
    private ThreadPoolExecutor httpConnPool = null;
    private ObjectMapper objectMapper;
    private boolean registerFlag = false;
    private boolean heartBeatFlag = false;

    @PostConstruct
    public void connectServer() {
        httpClientInit();
        httpConnPool.execute(() -> {
            //如果注册失败，会一直重试
            boolean isRegister = false;
            while (!isRegister) {
                isRegister = register();
                try {
                    Thread.sleep(registerInteral);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "注册成功，开始发送心跳。");
            heartbeat();
        });
    }

    private void httpClientInit() {
        //TODO 增加格式校验，域名或IP
        objectMapper = new ObjectMapper();
        xiaoQiangURLs = xiaoQiangConfigBean.getXiaoQiangURLs()[0].trim().split(",");
        httpCilent = HttpClients.createDefault();
        //queue不能为null
        httpConnPool = new ThreadPoolExecutor(1, 1, 180, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1));
        requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();

        nvps = new ArrayList<NameValuePair>();
        BasicNameValuePair portParam = new BasicNameValuePair("port", String.valueOf(this.port));
        BasicNameValuePair instaceNameParam = new BasicNameValuePair("instanceName", String.valueOf(this.xiaoQiangConfigBean.getInstanceName()));
        nvps.add(portParam);
        nvps.add(instaceNameParam);
    }

    /**
     * 注册实例,httpclient的几种post参数格式 参考：
     * https://blog.csdn.net/illbehere/article/details/72851045
     *
     * @return
     */
    public boolean register() {
        HttpPost httpPost = new HttpPost("http://" + xiaoQiangURLs[0] + "/conn/register");
        httpPost.setConfig(requestConfig);
        registerFlag = httpPostRequest(httpPost).isResult();
        return registerFlag;
    }


    /**
     * 取消注册实例
     *
     * @return
     */
    public boolean unregister() {
        HttpPost httpPost = new HttpPost("http://" + xiaoQiangURLs[0] + "/conn/unregister");
        httpPost.setConfig(requestConfig);
        HttpResult httpResult = httpPostRequest(httpPost);
        boolean result = httpResult.isResult();
        if(!result){
            System.out.println("取消注册失败");
        }
        return result;
    }


    /**
     * 发送心跳信息
     *
     * @return
     */
    public void heartbeat() {
        //请求uri前需要加协议信息
        HttpPost httpPost = new HttpPost("http://" + xiaoQiangURLs[0] + "/conn/heartbeat");
        httpPost.setConfig(requestConfig);
        while (registerFlag && !heartBeatFlag) {
            //10s发送一次心跳
            HttpResult httpResult = httpPostRequest(httpPost);
            try {
                Thread.sleep(heartBeatInteral);
                if (httpResult.isResult()) {
                    System.out.println("心跳发送成功");
                } else {
                    System.out.println("心跳发送失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpResult httpPostRequest(HttpPost httpPost) {
        byte[] bytes = new byte[256];
        HttpResult httpResult = new HttpResult(false);
        try {
            //一个主机可能有多个ip地址，如何能保证server端通过发送的ip可以找到client？
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpCilent.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            StringBuilder stringBuilder = new StringBuilder();
            int read = 0;
            do {
                read = inputStream.read(bytes);
                if (read > 0) {
                    stringBuilder.append(new String(bytes, 0, read, "utf-8"));
                }
            } while (read != -1);
            String content = stringBuilder.toString();
            httpResult = objectMapper.readValue(content, HttpResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            //此次请求失败
        }
        //使用Web项目中的jackson将json字符串转java对象，
        // 参考：https://www.cnblogs.com/winner-0715/p/6109225.html
        return httpResult;

    }

    //关闭时关闭Http连接，回收服务器端的资源。
    @PreDestroy
    public void destory() {
        if (httpCilent != null) {
            try {
                //不再发送心跳
                heartBeatFlag = false;
                //取消注册
                unregister();
                httpCilent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
