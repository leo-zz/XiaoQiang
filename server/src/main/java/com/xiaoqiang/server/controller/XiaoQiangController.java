package com.xiaoqiang.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoqiang.server.eneity.ClientAdd;
import com.xiaoqiang.server.eneity.EchartTreeNode;
import com.xiaoqiang.server.eneity.HttpResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.xiaoqiang.server.util.ServerCaches.clientsCaches;

@ResponseBody
@RequestMapping("/xq")
public class XiaoQiangController {
    //获取客户端列表
    @RequestMapping(value = "/clientlists", method = RequestMethod.POST)
    public List<ClientAdd> clientLists(){
        Set<String> keySet = clientsCaches.keySet();
        ArrayList<ClientAdd> clientlists = new ArrayList<>(keySet.size());
        keySet.forEach((key)->{
            clientlists.add(clientsCaches.get(key));
        });
        return clientlists;
    }

    //异常信息列表
    //TODO 如何优化controller中的http请求
    @RequestMapping(value = "/exceptions", method = RequestMethod.POST)
    public HttpResult<EchartTreeNode> excpetionsInfo(@RequestParam("instanceName") String instanceName){
        //获取地址
        ClientAdd clientAdd = clientsCaches.get(instanceName);
        String address = clientAdd.getAddress() + ":" + clientAdd.getPort();
        //请求异常数据
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://" + address+ "/exception/query");
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        String exceptionJson = httpPostRequest(httpPost, httpCilent);
        if(exceptionJson==null){
            return new HttpResult<EchartTreeNode>(false);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        new HttpResult<EchartTreeNode>(false);
        EchartTreeNode echartTreeNode=null;
        try {
            echartTreeNode = objectMapper.readValue(exceptionJson, EchartTreeNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
            return echartTreeNode==null ? new HttpResult<EchartTreeNode>(false): new HttpResult<EchartTreeNode>(true,echartTreeNode);
    }


    private String httpPostRequest(HttpPost httpPost,CloseableHttpClient httpCilent) {
        byte[] bytes = new byte[256];
        String content=null;
        try {
            //一个主机可能有多个ip地址，如何能保证server端通过发送的ip可以找到client？
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
            content = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //此次请求失败
        }
        return content;
    }
}

