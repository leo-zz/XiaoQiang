package com.xiaoqiang.client.retry;

import com.xiaoqiang.client.entity.ExceptionInfoEntity;
import com.xiaoqiang.client.entity.ExceptionInfos;
import com.xiaoqiang.client.util.CacheManager;
import com.xiaoqiang.client.util.ExceptionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 */
public class XQEnhencer {

    protected Object subject;
    private Random random = new Random();
    protected XiaoQiangRetryInfo xiaoQiangRetryInfo;

    //所有线程的异常都存放在这个map中，要能够定期清理过期的map，同时能够支持多线程并发异常。
    //确保随机数短时间内不会重叠
    private Map<Integer, Throwable> exMap = new HashMap<>();


    public XQEnhencer(Object subject, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        this.subject = subject;
        this.xiaoQiangRetryInfo = xiaoQiangRetryInfo;
    }

    public XQEnhencer(XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        this.xiaoQiangRetryInfo = xiaoQiangRetryInfo;
    }

    protected Object enhence(Method method, Object[] args, XiaoQiangRetryInfo xiaoQiangRetryInfo) throws InterruptedException, InvocationTargetException, IllegalAccessException {


        //1.0准备工作
        // 重复异常信息不记录
        boolean firstError = true;
        Object invoke = null;
        int retryCount=0;
        int actualRetryCount=0;
        int retryDelay=0;

        boolean result=true;
        List<ExceptionInfoEntity> exceptionInfoEntites=null;


        //1.1判断一个方法是否应该增强
        //1.2类上增加注解，所有方法都增强，参数共用
        if (xiaoQiangRetryInfo.isClassAnnotationed()) {
            retryCount=xiaoQiangRetryInfo.getRetryCount();
            retryDelay=xiaoQiangRetryInfo.getRetryDelay();
        }else{
            Map<String, XiaoQiangMethodRetryInfo> methodsInfo = xiaoQiangRetryInfo.getMethodsInfo();
            //1.3方法上增加注解，只增强特定方法，每个方法的参数可以不同
            if (!xiaoQiangRetryInfo.isMethodAnnotationed() || !methodsInfo.keySet().contains(method.getName())) {
                return method.invoke(subject, args);
            }else{
                XiaoQiangMethodRetryInfo methodRetryInfo = methodsInfo.get(method.getName());
                retryCount=methodRetryInfo.getRetryCount();
                retryDelay=methodRetryInfo.getRetryDelay();
            }
        }

        int exInt = 0;
        for (int i = retryCount+1; i > 0; i--) {
            actualRetryCount=(retryCount+1 - i);
            try {
                if (i <= retryCount) {
                    Thread.sleep(retryDelay);
                    System.out.println("等待" + retryDelay + "之后，再次重试");
                }
                invoke = method.invoke(subject, args);

                //第一次执行就成功，不记录异常信息；
                if(firstError){
                    System.out.println("方法执行成功，未进行重试！");
                }else{
                    //执行重试后成功，记录异常信息；
                    result=true;
                    System.out.println("方法经过"+actualRetryCount+"次重试后成功！");
                    ExceptionInfos exceptionInfos = new ExceptionInfos(result, actualRetryCount, exceptionInfoEntites);
                    CacheManager.put(exceptionInfos,System.currentTimeMillis());
                }
                break;
            } catch (Exception ex) {
                if (firstError) {
                    exceptionInfoEntites=new ArrayList<>();
                    System.out.println("第1次抛出异常！");
                    firstError = false;
                    exInt = random.nextInt();
                    //先打印，否则异常中的StackTrace不会记录上
                    ExceptionInfoEntity excInfo=ExceptionUtil.PrintExceptionInfo(ex);
                    exceptionInfoEntites.add(excInfo);
                    exMap.put(exInt, ex);
                } else {
                    System.out.println("第" + actualRetryCount + "次重试，仍出现异常！");
                    Throwable throwable = exMap.get(exInt);
                    //异常重复的判定
                    if (ExceptionUtil.ExceptionCompare(ex, throwable)) {
                        System.out.println("异常重复，不再打印和记录");
                    } else {
                        ExceptionInfoEntity excInfo1 = ExceptionUtil.PrintExceptionInfo(ex);
                        exceptionInfoEntites.add(excInfo1);
                    }
                    if (i == 1) {
                        //重试以失败结尾，保存失败的异常信息，
                        result=false;
                        System.out.println("执行" + retryCount + "次后仍然出错，异常抛出");
                        ExceptionInfos exceptionInfos = new ExceptionInfos(result, retryCount, exceptionInfoEntites);
                        CacheManager.put(exceptionInfos,System.currentTimeMillis());
                        throw ex;
                    }
                }
            }
        }
        return invoke;
    }
}