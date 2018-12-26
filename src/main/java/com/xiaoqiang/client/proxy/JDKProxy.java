//package com.xiaoqiang.client.proxy;
//
//import com.xiaoqiang.client.monitor.ThreadMonitor;
//import com.xiaoqiang.client.util.ExceptionUtil;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
///**
// * jdk自带方式实现动态代理，非线程安全
// */
//public class JDKEnhencer implements InvocationHandler {
//
//    private Object subject;
//    private int retryCount=5;
//    private int retryDelay=200;
//    private Random random = new Random();
//
//    //所有线程的异常都存放在这个map中，要能够定期清理过期的map，同时能够支持多线程并发异常。
//    //确保随机数短时间内不会重叠
//    Map<Integer, Throwable> exMap = new HashMap<>();
//
//    public JDKEnhencer(int retryCount, int retryDelay,Object subject) {
//        this.retryCount = retryCount;
//        this.retryDelay = retryDelay;
//        this.subject = subject;
//    }
//
//    public JDKEnhencer() {
//    }
//
//    //出现异常时重复执行5次。
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        //重复异常信息不记录
//        boolean firstError = true;
//        Object invoke=null;
//        int exInt=0;
//        for(int i=retryCount;i>0;i--) {
//            if(i<retryCount){
//                Thread.sleep(retryDelay);
//                System.out.println("等待"+retryDelay+"之后，再次执行");
//            }
//            try {
//
//                invoke=method.invoke(subject, args);
//
//                System.out.println("执行成功，无需重试！");
//                i = 0;
//            } catch (Exception ex) {
//                if (firstError) {
//                    System.out.println("第1次抛出异常！");
//                    firstError = false;
//                    exInt = random.nextInt();
//                    //先打印，否则异常中的StackTrace不会记录上
//                    ExceptionUtil.PrintExceptionInfo(ex);
//                    exMap.put(exInt, ex);
//                } else {
//                    System.out.println("第" + (retryCount - i) + "次重试，仍出现异常！");
//                    Throwable throwable = exMap.get(exInt);
//                    //异常重复的判定
//                    if(ExceptionUtil.ExceptionCompare(ex,throwable)){
//                        System.out.println("异常重复，不再打印和记录");
//                    }else{
//                        ExceptionUtil.PrintExceptionInfo(ex);
//                    }
//                    if (i == 1) {
//                        System.out.println("执行"+retryCount+"次后仍然出错，异常抛出");
//                        throw ex;
//                    }
//                }
//            }
//        }
//        return invoke;
//    }
//}
