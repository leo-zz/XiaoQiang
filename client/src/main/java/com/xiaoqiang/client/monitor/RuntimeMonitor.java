package com.xiaoqiang.client.monitor;

import com.sun.management.OperatingSystemMXBean;
import com.xiaoqiang.client.entity.RuntimeEntity;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 *      官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class RuntimeMonitor {
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    private static CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();


    /**
     *  JVM的厂商、名称等信息
     * @return
     */
    public static RuntimeEntity RuntimeInfo() {

        //返回JVM的名称
        String jvmName = runtimeMXBean.getName();
        //传入JVM的参数（不是main方法的参数），可以从多个地方加载JVM的参数：java命令行选项、环境变量、配置文件等。
        List<String> inputArguments = runtimeMXBean.getInputArguments();
        //返回JVM管理规范的版本
        String specVersion = runtimeMXBean.getManagementSpecVersion();
        //返回JVM规范的名称
        String specName = runtimeMXBean.getSpecName();
        //返回JVM启动近似时间（ms）
        long jvmStartTime = runtimeMXBean.getStartTime();
        //返回JVM厂商的名称
        String specVendor = runtimeMXBean.getSpecVendor();

        //返回操作系统的架构
        String arch = osmxb.getArch();
        //返回操作系统的名称
        String osName = osmxb.getName();
        //返回操作系统的版本
        String version = osmxb.getVersion();

        //返回编译器的名称,Just-in-time (JIT) compiler
        String compilerName = compilationMXBean.getName();
        //返回编译近似耗时（ms），如果多线程同时编译，那么该时间是各线程编译耗时之和。
        //该方法不能作为JVM性能的指标，也不能用来比较不同JVM实现的性能。
        long totalCompilationTime = compilationMXBean.getTotalCompilationTime();

        return new RuntimeEntity(jvmName,inputArguments,jvmStartTime,osName,compilerName,specVersion,specName);
    }

}
