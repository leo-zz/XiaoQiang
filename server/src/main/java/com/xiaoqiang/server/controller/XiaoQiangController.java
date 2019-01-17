package com.xiaoqiang.server.controller;

import com.xiaoqiang.server.eneity.ClientAdd;
import com.xiaoqiang.server.eneity.HttpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.xiaoqiang.server.util.ClientCaches.clientsCaches;

//不生效，仅仅是一个注明？
//@Controller
//如果未配置xiaoqiang.server.path，默认是/
@ResponseBody
@RequestMapping("${xiaoqiang.server.path:/}")
public class XiaoQiangController {
    /**
     * 借鉴zipkin的实现方式
     */
    @Value("classpath:xiaoqiang-ui/index.html")
    Resource indexHtml;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpResult clientRegister(@RequestParam("port") int port, @RequestParam("instanceName") String name, HttpServletRequest req) {
        String remoteAddr = req.getRemoteAddr();
        ClientAdd clientAdd = new ClientAdd(remoteAddr, port, name);
        clientsCaches.put(name, clientAdd);
        System.out.println("client：" + name + "进行注册，信息为：" + clientAdd);
        return new HttpResult(true);
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public HttpResult clientUnRegister(@RequestParam("instanceName") String name, HttpServletRequest req) {
        ClientAdd clientAdd = clientsCaches.get(name);
        if (clientAdd != null) {
            clientAdd.setActiveFlag(false);
            clientsCaches.put(name, clientAdd);
        }
        System.out.println("client：" + name + "取消注册");
        return new HttpResult(true);
    }


    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
    public HttpResult clientHeartBeat(@RequestParam("port") int port, @RequestParam("instanceName") String name, HttpServletRequest req) {
        ClientAdd clientAdd = clientsCaches.get(name);
        //考虑客户没有注册，直接发送心跳的情况
        if(clientAdd==null){
            String remoteAddr = req.getRemoteAddr();
            clientAdd = new ClientAdd(remoteAddr, port, name);
        }
        clientAdd.setActiveFlag(true);
        clientAdd.setLastConnTime(System.currentTimeMillis());
        clientsCaches.put(name, clientAdd);
        System.out.println("更新client：" + name + "，通信时间:" + new Date(clientAdd.getLastConnTime()));
        return new HttpResult(true);
    }

    @RequestMapping(value = "/clientlists", method = RequestMethod.POST)
    public List<ClientAdd> clientLists(){
        Set<String> keySet = clientsCaches.keySet();
        ArrayList<ClientAdd> clientlists = new ArrayList<>(keySet.size());
        keySet.forEach((key)->{
            clientlists.add(clientsCaches.get(key));
        });
        return clientlists;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource> index(HttpServletRequest request, Map<String, Object> model) {

        return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().cacheControl(CacheControl.maxAge(1L, TimeUnit.MINUTES))).body(this.indexHtml);
    }
}

