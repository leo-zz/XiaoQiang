package com.xiaoqiang.client.monitor;

import com.xiaoqiang.client.entity.ClassEntity;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 *      官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class ClassMonitor {
    private static ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
    private static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    //JVM类加载系统的管理接口
    public static ClassEntity ClassLoadInfo() {

        //返回当前JVM中已加载的类的数量（加载过之后卸载的不算）
        int loadedClassCount = classLoadingMXBean.getLoadedClassCount();
        //返回当前JVM从启动后加载的类的总数量
        long totalLoadedClassCount = classLoadingMXBean.getTotalLoadedClassCount();
        //返回当前JVM从启动后卸载的类的总数量
        long unloadedClassCount = classLoadingMXBean.getUnloadedClassCount();
        //是否详细输出类加载信息
        boolean verbose = classLoadingMXBean.isVerbose();
        //bootstrap类加载器用于检索类文件的路径。多个路径之间会被路径分隔符分割。
        String bootClassPath = runtimeMXBean.getBootClassPath();
        //系统类加载器检索类文件的路径
        String classPath = runtimeMXBean.getClassPath();

        return new ClassEntity(loadedClassCount,totalLoadedClassCount,unloadedClassCount,bootClassPath,classPath);
    }

}
