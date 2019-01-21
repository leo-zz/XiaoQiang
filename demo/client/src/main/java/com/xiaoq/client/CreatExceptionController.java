package com.xiaoq.client;

import com.xiaoqiang.client.annotation.XiaoQiangRetry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test1")
@XiaoQiangRetry(retryCount = 2, retryDelay = 1000)
public class CreatExceptionController {

    @RequestMapping("/mod/{a}/{b}")
    public String testmod(@PathVariable("a") int a,@PathVariable("b") int b){
        String s = String.valueOf(a % b);
        System.out.println(a+ " % "+b+"="+s);
        return s;
    }

    @RequestMapping("/div/{a}/{b}")
    public String testdiv(@PathVariable("a") int a,@PathVariable("b") int b){
        String s = String.valueOf(a / b);
        System.out.println(a+ " / "+b+"="+s);
        return s;
    }
}
