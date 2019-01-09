package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.entity.ExceptionInfos;
import com.xiaoqiang.client.util.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("query")
    public List<ExceptionInfos> queryExceptionInfos(){

        return CacheManager.query(System.currentTimeMillis());
    }

}
