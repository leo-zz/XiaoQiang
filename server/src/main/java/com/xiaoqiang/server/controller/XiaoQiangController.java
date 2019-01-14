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
import java.util.Map;
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
    @Value("classpath:xiaoqiang-ui/monitor.html")
    Resource indexHtml;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public HttpResult clientRegister(@RequestParam("port") int port, @RequestParam("instanceName") String name, HttpServletRequest req){
        String remoteHost = req.getRemoteHost();
        int remotePort = req.getRemotePort();
        String remoteAddr = req.getRemoteAddr();
        System.out.println(" port:  "+port);
        System.out.println(" remoteAddr:  "+remoteAddr);
        clientsCaches.put(name,new ClientAdd(remoteAddr,remotePort));
        HttpResult result = new HttpResult();
        result.setResult(true);
        return result;
    }

    @RequestMapping(value = "/unregister",method = RequestMethod.POST)
    public HttpResult clientUnRegister(){
        HttpResult result = new HttpResult();
        result.setResult(true);
        return result;
    }

    @RequestMapping(value = "/heartbeat",method = RequestMethod.POST)
    public HttpResult clientHeartBeat(){
        HttpResult result = new HttpResult();
        result.setResult(true);
        return result;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource> index(HttpServletRequest request, Map<String, Object> model) {

        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().cacheControl(CacheControl.maxAge(1L, TimeUnit.MINUTES))).body(this.indexHtml);
    }

}
