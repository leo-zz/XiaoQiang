package com.xiaoq.client;

//import com.xiaoqiang.client.annotation.XiaoQiangRetry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test2")
public class Controller2 {
    
    @RequestMapping("/calc/{a}/{b}")
//    @XiaoQiangRetry(retryCount = 5, retryDelay = 300)
    public String testdiv(@PathVariable("a") int a,@PathVariable("b") int b){
        String s = String.valueOf(a / b);
        System.out.println(a+ " / "+b+"="+s);
        return s;
    }
}
