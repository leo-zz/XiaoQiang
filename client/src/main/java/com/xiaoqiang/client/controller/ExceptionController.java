package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.entity.EchartTreeNode;
import com.xiaoqiang.client.entity.ExceptionInfos;
import com.xiaoqiang.client.entity.HttpResult;
import com.xiaoqiang.client.util.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("query")
    public HttpResult<EchartTreeNode> queryExceptionInfos(){

        List<ExceptionInfos> lists= CacheManager.query(System.currentTimeMillis());
        EchartTreeNode root = new EchartTreeNode();
        //Lambda
        lists.forEach(ex->{

        });
        return new HttpResult(true,root);
    }

}
