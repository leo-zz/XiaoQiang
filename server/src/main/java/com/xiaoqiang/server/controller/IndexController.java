package com.xiaoqiang.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//不生效，仅仅是一个注明？
//@Controller
//如果未配置xiaoqiang.server.path，默认是/
@ResponseBody
@RequestMapping("${xiaoqiang.server.path:/}")
public class IndexController {
    /**
     * 借鉴zipkin的实现方式
     */
    @Value("classpath:xiaoqiang-ui/index.html")
    Resource indexHtml;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Resource> index(HttpServletRequest request, Map<String, Object> model) {

        return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().cacheControl(CacheControl.maxAge(1L, TimeUnit.MINUTES))).body(this.indexHtml);
    }
}

