package com.xiaoqiang.client.monitor;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import com.xiaoqiang.client.monitor.entity.*;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class MemoryMonitor {
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    private static ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();


    /**
     * JVM中的内存系统管着着下属几种内存：
     * 堆
     * 堆是存放所有类实例和数组的运行时数据区，在JVM启动时创建。由被称为GC的内存管理系统回收。
     * 堆可能有固定的大小，或者可以扩充和缩小，可由-Xms -Xmx设定。堆不需要连续的空间（所谓的主内存）。
     * 非堆：
     * 方法区，被所有线程共享，它保存了类的结构，比如运行时常量池、属性和方法的数据，方法和构造器的代码。
     * 逻辑上，方法区是堆的一部分，但是JVM可以不对它进行GC。此外，需要额外的内存空间执行JVM的执行和优化，
     * 比如JIT编译器为了优化JVM字节码的执行，而存储对应的原生机器码
     * <p>
     * 内存使用情况监控包括：应用的内存使用情况，自动内存管理系统的负载，潜在的内存泄露
     * 内存使用监控的三种方式：Polling、使用阈值通知、回收阈值通知。
     * 内存使用监控机制是为负载均衡或分布式系统使用的，当主机的内存使用超过设定的阈值时，应用不再接收新的任务。
     * <p>
     * 通知
     * MemoryMXBean提供“超出内存使用阈值”、“超出GC内存阈值”两种消息通知功能，允许监听器注册。
     * 超出内存使用阈值通知：当内存池的内存使用情况超出设定的 usage threshold值。
     * 超出GC内存阈值通知：当JVM在回收内存池中的垃圾对象后，内存池的内存使用情况仍超出设定的collection usage threshold值。
     */
    private static JVMMemory JVMMemoryInfo() {

        //通过MXBean拿到JVM内存使用情况
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        //堆内存使用的字节数
        long heapUsedMemory = heapMemoryUsage.getUsed();
        //当前堆内存申请使用内存空间的字节数
        long heapCommittedMemory = heapMemoryUsage.getCommitted();
        //非堆内存使用的字节数
        long nonHeapUsedMemory = nonHeapMemoryUsage.getUsed();
        //当前非堆内存申请使用内存空间的字节数
        long nonHeapCommittedMemory = nonHeapMemoryUsage.getCommitted();

        //允许当前进程使用的虚拟内存的容量（字节数），是否等于nonHeapCommittedMemory+heapCommittedMemory？
        long committedVirtualMemorySize = osmxb.getCommittedVirtualMemorySize();

        return new JVMMemory(heapUsedMemory, heapCommittedMemory, nonHeapUsedMemory, nonHeapCommittedMemory, committedVirtualMemorySize);

//        //等待销毁的对象数
//        int objectPendingFinalizationCount = memoryMXBean.getObjectPendingFinalizationCount();
//        //手动触发GC
//        memoryMXBean.gc();
//        //是否输出内存系统的详细信息
//        boolean verbose = memoryMXBean.isVerbose();

    }

    private static List<MemoryPool> MemoryPoolInfos() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        ArrayList<MemoryPool> memoryPools = new ArrayList<>(memoryPoolMXBeans.size());
        /**
         *  内存池是JVM的内存管理器管理的内存资源。
         *  内存池有两种类型：堆或非堆。
         *
         *  内存池的四种属性：
         *  内存使用
         *      getUsage()方法可以拿到当前内存池使用情况的估值。对于会进行GC的内存池，内存使用是可达和不可达两种类型对象锁占用的内存和。
         *      通常，估算内存使用情况是轻量的操作，但是对于对象没有连续保存的内存池，这个操作会比较费时。
         *
         *  峰值使用
         *      JVM记录了内存池的内存使用峰值，使用getPeakUsage()方法来获取。
         *  使用阈值
         *      JVM为每个内存池设定了一个管理属性：使用阈值，它的默认值与平台有关。当它为正数时，会开启内存使用超过阈值检查。
         *  JVM会在适当的时机执行内存使用阈值检查工作，使用getUsageThresholdCount()可以查看内存使用超过阈值的次数。
         *  该参数主要是为了以低开销地方式监控内存使用的增长趋势。
         *
         *  collection usage threshold
         *     GC使用阈值适合用于一些有GC功能的内存池，当JVM进行GC回收无用的对象时，内存池中仍然有一些
         *  内存处于使用状态。collection usage threshold就是设定GC执行后内存使用的阈值。
         *     JVM虚拟机在GC时进行collection usage threshold检查。
         *     有些具有GC功能的内存池不支持collection usage threshold，比如持续的并发GC收集器，
         *     在GC过程中允许其他线程同时为新对象分配内存
         */
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            //获取最近一次GC完成后的内存使用情况，即collection usage
            MemoryUsage lastGCCollectionUsage = memoryPoolMXBean.getCollectionUsage();

            //获取该内存池中collection usage threshold的值
            long collectionUsageThreshold=0 ;
            if(memoryPoolMXBean.isCollectionUsageThresholdSupported())
                collectionUsageThreshold=memoryPoolMXBean.getCollectionUsageThreshold();
            //获取该内存池内存使用超过collection usage threshold的次数
            long collectionUsageThresholdCount=0;
            if(memoryPoolMXBean.isCollectionUsageThresholdSupported())
            collectionUsageThresholdCount = memoryPoolMXBean.getCollectionUsageThresholdCount();
            //返回内存池的内存管理员的名称，每个内存池至少有一个内存管理员。
            String[] memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
            //返回内存池的名称
            String name = memoryPoolMXBean.getName();
            //返回内存使用情况的峰值
            MemoryUsage peakUsage = memoryPoolMXBean.getPeakUsage();
            //返回内存池的类型
            MemoryType type = memoryPoolMXBean.getType();
            //返回内存池的内存使用情况。
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            MemoryPool memoryPool = new MemoryPool(usage, type, peakUsage, name, memoryManagerNames, lastGCCollectionUsage);
            memoryPools.add(memoryPool);
        }
        return memoryPools;
    }


    private static List<MemoryManager> MemoryManagerInfos() {

        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
        List<MemoryManager> memoryManagers = new ArrayList<>(memoryManagerMXBeans.size());
        //JVM的内存管理，一个memory manager管理多个JVM内存池
        for (MemoryManagerMXBean mmMXbean : memoryManagerMXBeans) {
            //返回memory manager管理的内存池的名称
            String[] memoryPoolNames = mmMXbean.getMemoryPoolNames();
            //memory manager的名称
            String name = mmMXbean.getName();
            //memory manager是否有效
            boolean valid = mmMXbean.isValid();
            memoryManagers.add(new MemoryManager(name, memoryPoolNames, valid));
        }
        return memoryManagers;
    }

    /**
     * 获取内存使用率
     * linux中swap的介绍https://www.cnblogs.com/kerrycode/p/5246383.html
     * linux中将内存分成一页一页的，当物理内存不够用时，操作系统会将长时间不使用的内存页保存到预先配置的硬盘空间，
     * 即Swap Space，以释放一些内存空间供系统中的进程使用。
     * VirtualMemory=PhysicalMemory+SwapSpace
     *
     * @return
     */
    private static HostMemoryInfo HostMemoryInfo() {

        //物理内存的总容量（字节数）
        long totalPhysicalMemorySize = osmxb.getTotalPhysicalMemorySize();
        //可用物理内存的容量（字节数）
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();


        //Swap内存的总容量（字节数），Swap频繁使用，说明内存不够。
        long totalSwapSpaceSize = osmxb.getTotalSwapSpaceSize();
        //可用Swap内存的容量（字节数）
        long freeSwapSpaceSize = osmxb.getFreeSwapSpaceSize();

        //获取JVM为指定线程分配的堆内存的大小（字节数），
        long threadAllocatedBytes = threadMXBean.getThreadAllocatedBytes(1);

//        System.out.println("freePhysicalMemorySize："+freePhysicalMemorySize/1024/1024+"MB");
//        System.out.println("totalPhysicalMemorySize："+totalPhysicalMemorySize/1024/1024+"MB");
//        Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalPhysicalMemorySize) * 100;
//        String str = "物理内存使用率:" + compare.intValue() + "%";
        return new HostMemoryInfo(totalPhysicalMemorySize, freePhysicalMemorySize, totalSwapSpaceSize, freeSwapSpaceSize);
    }

    public static MemoryEntity MemoryInfo() {
        MemoryEntity memoryEntity = new MemoryEntity();
        memoryEntity.setJvmMemory(JVMMemoryInfo());
        memoryEntity.setHostMemoryInfo(HostMemoryInfo());
        memoryEntity.setMemoryPools(MemoryPoolInfos());
        memoryEntity.setMemoryManagers(MemoryManagerInfos());
        return memoryEntity;
    }

}
