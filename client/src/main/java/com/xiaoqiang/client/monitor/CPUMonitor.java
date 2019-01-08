package com.xiaoqiang.client.monitor;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.xiaoqiang.client.entity.CPUEntity;

import java.lang.management.ManagementFactory;

/**
 * 官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class CPUMonitor {
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();

    /**
     *
     */
    public static CPUEntity CPUInfo() {

        //获取JVM所在进程消耗的CPU时间，单位是ns，该值并不是ns级别精确。
        //可以通过手动采样计算该进程的CPU负荷，参考https://blog.csdn.net/blog4j/article/details/17122061
        long processCpuTime = osmxb.getProcessCpuTime();

        //当前可用的CPU核心数
        int availableProcessors = osmxb.getAvailableProcessors();
        //JVM实例的CPU的负载，[0.0,1.0] 。如果为负则表示获取不到该信息
        double processCpuLoad = osmxb.getProcessCpuLoad();
        //整个系统的CPU的负载，[0.0,1.0] 。如果为负则表示获取不到该信息
        double systemCpuLoad = osmxb.getSystemCpuLoad();

        /**
         * 某些平台上无法获取系统的负荷信息,则返回负值，比如windows平台，获取信息的成本太高。
         * 统计上一分钟系统的平均负荷，通过计算处理器正在运行和等待处理器的线程数之和。
         */
        double systemLoadAverage = osmxb.getSystemLoadAverage();

        /**
         * 参考https://www.cnblogs.com/shengs/p/5148284.html
         * CPU时间组成
         CPU的工作时间由三部分组成：用户态时间、系统态时间和空闲态时间。具体的组成为：
         CPU时间包含User time、System time、Nice time、Idle time、Waiting time、Hardirq time、Softirq time、Steal time
         空闲态时间==idle time
         用户态时间==user time+ Nice time。
         内核态时间==system time+ Hardirq time+ Softirq time。
         user time。指CPU在用户态执行进程的时间。
         system time。指CPU在内核运行的时间。
         nice time。指系统花费在调整进程优先级上的时间。
         idle time。系统处于空闲期，等待进程运行。
         waiting time。指CPU花费在等待I/O操作上的总时间，与blocked相似。
         steal time。指当前CPU被强制（involuntary wait ）等待另外虚拟的CPU处理完毕时花费的时间，此时 hypervisor 在为另一个虚拟处理器服务。
         Softirq time 、Hardirq time。分别对应系统在处理软硬中断时候所花费的CPU时间。
         */
        //拿到指定线程消耗的CPU时间长度（ns）要求线程ID存在且线程存活，线程支持测量CPU时间。
        //CPU的消耗分为用户进程消耗和系统进程消耗。
        long threadCpuTime = threadMXBean.getThreadCpuTime(1);
        //拿到指定线程消耗的用户进程CPU时间长度（ns）
        long threadUserTime = threadMXBean.getThreadUserTime(1);

        return new CPUEntity(availableProcessors,processCpuLoad,systemCpuLoad,systemLoadAverage);
    }

}
