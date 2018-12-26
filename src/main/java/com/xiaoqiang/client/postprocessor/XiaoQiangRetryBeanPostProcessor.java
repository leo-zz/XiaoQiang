package com.xiaoqiang.client.postprocessor;

import com.xiaoqiang.client.configuration.XiaoQiangRetryConfigBean;
import com.xiaoqiang.client.util.ExceptionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class XiaoQiangRetryBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private XiaoQiangRetryConfigBean xiaoQiangRetryConfigBean;

    /**
     * 参数加载：控制台上的参数>注解上的参数>yaml上配置的参数
     * 如果类上增加@XiaoQiangRetry，那么该类下的所有方法都会错误重试
     * 如果方法上增加@XiaoQiangRetry，那么该方法会错误重试
     *
     * @return 无论是包装后的bean还是初始bean，都要返回一个，自定义的BeanPostProcessor不要返回空。
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        //0.0初始化资源
        XiaoQiangRetryInfo xiaoQiangRetryInfo = null;
        ArrayList<String> methodNames = new ArrayList<>();
        boolean isClassAnnotation = false;

        //1.0解读类上的注解。
        /**
         *  getDeclaredAnnotations方法返回当前类直接引用的注解
         *  getAnnotatedInterfaces()方法返回的是当前类实现的接口信息
         */
        Annotation[] annotations = bean.getClass().getDeclaredAnnotations();

        //1.1遍历类的所有注解
        for (Annotation annotation : annotations) {
            //注解的名字不要拿错了
            String typeName = annotation.annotationType().getName();
            //1.2如果类上有XiaoQiangRetry注解，那么将isAnnotation置为true
            if (typeName.equals("com.xiaoqiang.client.annotation.XiaoQiangRetry")) {
                System.out.println("**********类" + beanName + "中包含XiaoQiangRetry注解了");
                isClassAnnotation = true;
            }
        }

        //2.0如果类上没有XiaoQiangRetry注解，则遍历方法
        if (!isClassAnnotation) {
            Method[] declaredMethods = bean.getClass().getDeclaredMethods();
            //2.1遍历方法
            for (Method declaredMethod : declaredMethods) {
                String methodName = declaredMethod.getName();
                Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    String name = declaredAnnotation.annotationType().getName();
                    if (name.equals("com.xiaoqiang.client.annotation.XiaoQiangRetry")) {
                        methodNames.add(methodName);
                        System.out.println("**********方法" + methodName + "中包含XiaoQiangRetry注解了");
                        //增强该方法，实际上也是给这个类创建代理，不过只增强特定的方法
                    }
                }
            }
        }

        //3.0将注解信息封装到XiaoQiangRetryInfo中传递给代理类
        if (isClassAnnotation) {
            xiaoQiangRetryInfo = new XiaoQiangRetryInfo(beanName, isClassAnnotation);
            return enhenceBean(bean, xiaoQiangRetryInfo);
        } else if (methodNames.size() > 0) {
            xiaoQiangRetryInfo = new XiaoQiangRetryInfo(beanName, methodNames);
            return enhenceBean(bean, xiaoQiangRetryInfo);
        } else {
            return bean;
        }
    }

    /**
     * 增强bean中的所有方法，正常业务逻辑处理类，不会包含get/set方法、object中的方法
     *
     * @param bean 传入的原始bean
     * @return 增强后的bean
     */
    private Object enhenceBean(Object bean, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
        if ("cglib".equals(xiaoQiangRetryConfigBean.getProxyWay())) {
            Object instance = new CglibEnhencer(xiaoQiangRetryConfigBean.getRetryCount(), xiaoQiangRetryConfigBean.getRetryDelay(), xiaoQiangRetryInfo).getInstance(bean);
            return instance;
        } else {
            JDKEnhencer handler = new JDKEnhencer(xiaoQiangRetryConfigBean.getRetryCount(), xiaoQiangRetryConfigBean.getRetryDelay(), bean, xiaoQiangRetryInfo);
            Object instance = Proxy.newProxyInstance(handler.getClass().getClassLoader(), bean.getClass().getInterfaces(), handler);
            return instance;
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    class XiaoQiangRetryInfo {

        private String beanName;
        private boolean classAnnotationed;
        private boolean methodAnnotationed;
        private List<String> methodNames;

        public XiaoQiangRetryInfo(String beanName, boolean classAnnotationed) {
            this.beanName = beanName;
            this.classAnnotationed = classAnnotationed;
            this.methodNames = null;
            this.methodAnnotationed = false;
        }

        public XiaoQiangRetryInfo(String beanName, List<String> methodNames) {
            this.beanName = beanName;
            this.classAnnotationed = false;
            this.methodAnnotationed = true;
            this.methodNames = methodNames;
        }

        public String getBeanName() {
            return beanName;
        }

        public boolean isClassAnnotationed() {
            return classAnnotationed;
        }

        public boolean isMethodAnnotationed() {
            return methodAnnotationed;
        }

        public List<String> getMethodNames() {
            return methodNames;
        }
    }


    /**
     * Cglib方式实现动态代理，非线程安全
     */
    class CglibEnhencer extends XQEnhencer implements MethodInterceptor {

        public CglibEnhencer(int retryCount, int retryDelay, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
            super(retryCount, retryDelay, xiaoQiangRetryInfo);
        }

        public Object getInstance(Object subject) {
            super.subject = subject;
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(super.subject.getClass());
            enhancer.setCallback(this);
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return super.enhence(method, args, xiaoQiangRetryInfo);
        }
    }


    /**
     * jdk自带方式实现动态代理，非线程安全
     */
    public class JDKEnhencer extends XQEnhencer implements InvocationHandler {


        public JDKEnhencer(int retryCount, int retryDelay, Object subject, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
            super(subject, retryCount, retryDelay, xiaoQiangRetryInfo);
        }

        //出现异常时重复执行5次。
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return super.enhence(method, args, xiaoQiangRetryInfo);
        }
    }

    abstract class XQEnhencer {

        private Object subject;
        private int retryCount;
        private int retryDelay;
        private Random random = new Random();
        protected XiaoQiangRetryInfo xiaoQiangRetryInfo;

        //所有线程的异常都存放在这个map中，要能够定期清理过期的map，同时能够支持多线程并发异常。
        //确保随机数短时间内不会重叠
        private Map<Integer, Throwable> exMap = new HashMap<>();


        public XQEnhencer(Object subject, int retryCount, int retryDelay, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
            this.subject = subject;
            this.retryCount = retryCount;
            this.retryDelay = retryDelay;
            this.xiaoQiangRetryInfo = xiaoQiangRetryInfo;
        }

        public XQEnhencer(int retryCount, int retryDelay, XiaoQiangRetryInfo xiaoQiangRetryInfo) {
            this.retryCount = retryCount;
            this.retryDelay = retryDelay;
            this.xiaoQiangRetryInfo = xiaoQiangRetryInfo;
        }

        protected Object enhence(Method method, Object[] args, XiaoQiangRetryInfo xiaoQiangRetryInfo) throws InterruptedException, InvocationTargetException, IllegalAccessException {

            /**
             *  判断一个方法是否应该增强
             *  1.类上增加注解
             *  2.方法上增加注解
             */
            if (!xiaoQiangRetryInfo.isClassAnnotationed()) {
                if (!xiaoQiangRetryInfo.isMethodAnnotationed() || !xiaoQiangRetryInfo.methodNames.contains(method.getName())) {
                    return method.invoke(subject, args);
                }
            }

            //重复异常信息不记录
            boolean firstError = true;
            Object invoke = null;

            int exInt = 0;
            for (int i = retryCount; i > 0; i--) {
                try {
                    if (i < retryCount) {
                        Thread.sleep(retryDelay);
                        System.out.println("等待" + retryDelay + "之后，再次执行");
                    }
                    invoke = method.invoke(subject, args);
                    System.out.println("执行成功，无需重试！");
                    i = 0;
                } catch (Exception ex) {
                    if (firstError) {
                        System.out.println("第1次抛出异常！");
                        firstError = false;
                        exInt = random.nextInt();
                        //先打印，否则异常中的StackTrace不会记录上
                        ExceptionUtil.PrintExceptionInfo(ex);
                        exMap.put(exInt, ex);
                    } else {
                        System.out.println("第" + (retryCount - i) + "次重试，仍出现异常！");
                        Throwable throwable = exMap.get(exInt);
                        //异常重复的判定
                        if (ExceptionUtil.ExceptionCompare(ex, throwable)) {
                            System.out.println("异常重复，不再打印和记录");
                        } else {
                            ExceptionUtil.PrintExceptionInfo(ex);
                        }
                        if (i == 1) {
                            System.out.println("执行" + retryCount + "次后仍然出错，异常抛出");
                            throw ex;
                        }
                    }
                }
            }
            return invoke;
        }
    }
}
