package com.xiaoqiang.client.monitor;

import com.sun.management.ThreadMXBean;
import com.xiaoqiang.client.monitor.entity.ThreadEntity;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

/**
 * 官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class ThreadMonitor {
    private static ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();

    /**
     * Thread ID
     * 线程号唯一标识一个线程，是长整型的正值。当线程销毁后，这个编号可能会被其他线程重新使用。
     * Thread CPU time
     * 线程消耗的CPU时间长度，支持所有线程进行CPU耗时检测的JVM也允许当前线程的CPU时间耗时检测。
     * 同样是以ns为单位，但是不是ns。
     * 使用isThreadCpuTimeSupported可以查看JVM是否检测线程的CPU耗时。通过isThreadCpuTimeEnabled和
     * setThreadCpuTimeEnabled可以查看和设置开启此功能。
     * Thread Contention Monitoring线程争夺监控
     * Thread Contention的含义：一个线程在等待另外一个线程持有的对象或锁等资源，比如两个线程对同一个整数进行原子性的增加操作
     * ，那么它们就在争夺该整数资源。如果原本持有资源的线程没有运行，那么等待线程会执行的更快。参考https://stackoverflow.com/questions/1970345/what-is-thread-contention
     * <p>
     * 当开启了线程Thread Contention监控后，如果存在线程处于同步阻塞或等待通知的情况，JVM会记录线程的
     * 等待时间到ThreadInfo对象中。该功能默认是关闭的，通过isThreadContentionMonitoringSupported和setThreadContentionMonitoringEnabled
     * 可以查看和设置功能的开启状态。
     * 同步信息和死锁的检测
     * 一些JVM允许监控 object monitor 和  ownable synchronizer 的使用。
     * <p>
     * ThreadInfo
     * ThreadInfo包含了关于线程的信息：
     * 1、常规线程信息：线程ID和线程名称。
     * 2、执行信息：
     * 线程状态，
     * 线程阻塞等待的对象（等待进入同步块或方法，等待Object.wait，执行LockSupport.park后等待）
     * 拥有当前线程等待的对象的线程ID。
     * 线程的栈信息。
     * 当前线程锁定的monitors资源和同步锁。
     * 3、同步统计信息
     * 当前线程被阻塞的次数，线程被阻塞的总时长。
     *
     * @return
     */
    public static ThreadEntity ThreadInfo() {
        //当前存活的线梳理（含守护线程）
        int threadCount = threadMXBean.getThreadCount();
        //寻找deadlock死锁的线程，操作比较耗时
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        //寻找monitor deadlocked死锁线程，操作比较耗时
        long[] monitorDeadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
        //线程的峰值数量
        int peakThreadCount = threadMXBean.getPeakThreadCount();
        //所有存活线程的栈和同步信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        //自JVM启动以来，所有创建并启动的线程数
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();

        int deadlockedThreadsNum = deadlockedThreads!=null?deadlockedThreads.length:0;
        int monitorDeadlockedThreadsNum = monitorDeadlockedThreads!=null?monitorDeadlockedThreads.length:0;
        return new ThreadEntity(threadCount, peakThreadCount, totalStartedThreadCount,deadlockedThreadsNum, monitorDeadlockedThreadsNum);
    }
}
