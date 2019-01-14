package com.xiaoqiang.client.util;


import com.xiaoqiang.client.configuration.XiaoQiangConfigBean;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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
    private int  port;

    @Autowired
    XiaoQiangConfigBean xiaoQiangConfigBean;

    List<NameValuePair> nvps=null;
    private String[] xiaoQiangURLs;
    private CloseableHttpClient httpCilent = null;
    private RequestConfig requestConfig = null;
    private ThreadPoolExecutor httpConnPool = null;
    private boolean registerFlag = false;
    private boolean heartBeatFlag = false;

    @PostConstruct
    public void connectServer() {

        httpClientInit();

        if (register()) {
            new Thread(() -> {
                System.out.println();
                heartbeat();
            }).start();
        }
        ;

    }

    private void httpClientInit() {
        //TODO 增加格式校验，域名或IP
        xiaoQiangURLs = xiaoQiangConfigBean.getXiaoQiangURLs()[0].trim().split(",");
        httpCilent = HttpClients.createDefault();
        //queue不能为null
        httpConnPool = new ThreadPoolExecutor(1, 1, 180, TimeUnit.SECONDS,  new LinkedBlockingDeque<Runnable>());
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
        int count = 0;
        byte[] bytes = new byte[256];
        HttpPost httpPost = new HttpPost("http://" + xiaoQiangURLs[0] + "/register");
        httpPost.setConfig(requestConfig);

        while (!registerFlag) {
            count++;
            if (count >= 100) {
                break;
            }
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
                System.out.println("收到响应的内容：" + stringBuilder.toString());
                registerFlag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count < 100 && !registerFlag;
    }


    /**
     * 取消注册实例
     *
     * @return
     */
    public boolean unregister() {

        byte[] bytes = new byte[256];
        HttpPost httpPost = new HttpPost("http://" + xiaoQiangURLs[0] + "/unregister");
        httpPost.setConfig(requestConfig);
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
            System.out.println("收到响应的内容：" + stringBuilder.toString());
            registerFlag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 发送心跳信息
     *
     * @return
     */
    public boolean heartbeat() {
        byte[] bytes = new byte[256];
        HttpPost httpPost = new HttpPost(xiaoQiangURLs[0] + "/unregister");
        httpPost.setConfig(requestConfig);
        while (registerFlag && !heartBeatFlag) {
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
                System.out.println("收到响应的内容：" + stringBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //关闭时关闭Http连接，回收服务器端的资源。
    @PreDestroy
    public void destory() {
        if (httpCilent != null) {
            try {
                httpCilent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
