package com.xiaoqiang.client.monitor;

import com.sun.management.GcInfo;
import com.xiaoqiang.client.monitor.entity.GCEntity;

import javax.management.openmbean.CompositeType;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 官方API ：https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/ThreadMXBean.html
 */
public class GCMonitor {
    private static List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

    //JVM垃圾回收的信息，垃圾回收器是 MemoryManager的一种
    public static String GCInfo() {
//        List<GCEntity> gcEntities = new ArrayList<>(garbageCollectorMXBeans.size());
        String returnStr="";
        for (GarbageCollectorMXBean gcMXBean : garbageCollectorMXBeans) {
            GCEntity gcEntity = new GCEntity();
            //输出GC的总次数
            long collectionCount = gcMXBean.getCollectionCount();
            //输出GC的总耗时。只是近似值，如果GC的时间特别短，GC总耗时可能不变。
            long collectionTime = gcMXBean.getCollectionTime();
            /**
             *      当前内存管理者管理的内存池的名称
             *
             *      默认GC收集器是Parallel多线程并行，GC线程和App线程取一运行，即Stop the world
             *      PS Scavenge
             *      0 = "PS Eden Space" 1 = "PS Survivor Space"
             *      PS MarkSweep
             *      0 = "PS Eden Space" 1 = "PS Survivor Space" 2 = "PS Old Gen"
             *
             *      指定-XX:+UseParNewGC
             *      ParNew
             *      0 = "Par Eden Space" 1 = "Par Survivor Space"
             *      MarkSweepCompact
             *      0 = "Par Eden Space" 1 = "Par Survivor Space" 2 = "Tenured Gen"
             *
             *      指定-XX:+UseConcMarkSweepGC  或
             *      同时指定 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
             *      ParNew
             *      0 = "Par Eden Space" 1 = "Par Survivor Space"
             *      ConcurrentMarkSweep
             *      0 = "Par Eden Space" 1 = "Par Survivor Space" 2 = "CMS Old Gen"
             *
             */
            String[] memoryPoolNames = gcMXBean.getMemoryPoolNames();
            //内存管理器的名称
            String name = gcMXBean.getName();

            gcEntity.setCollectionCount(collectionCount);
            gcEntity.setCollectionTime(collectionTime);
            gcEntity.setMemoryPoolNames(memoryPoolNames);
            gcEntity.setName(name);


            com.sun.management.GarbageCollectorMXBean gc = (com.sun.management.GarbageCollectorMXBean) gcMXBean;
            //返回最近一次的GC信息
            GcInfo gcInfo = gc.getLastGcInfo();
            if (gcInfo != null) {
                //返回GC开始前所有内存池的内存使用情况
                Map<String, MemoryUsage> memoryUsageBeforeGc = gcInfo.getMemoryUsageBeforeGc();
                //返回GC结束时所有内存池的内存使用情况
                Map<String, MemoryUsage> memoryUsageAfterGc = gcInfo.getMemoryUsageAfterGc();
                //返回当前composite data instance 复合数据实例的复合类型
                CompositeType compositeType = gcInfo.getCompositeType();
                //返回此次GC执行耗时（ms）
                long duration = gcInfo.getDuration();
                //返回此次GC的编号
                long id = gcInfo.getId();
                //返回此次GC开始执行的时间（ms）
                long startTime = gcInfo.getStartTime();
                //返回此次GC执行结束的时间（ms）
                long endTime = gcInfo.getEndTime();
                gcEntity.setMemoryUsageBeforeGc(memoryUsageBeforeGc);
                gcEntity.setMemoryUsageAfterGc(memoryUsageAfterGc);
                gcEntity.setDuration(duration);
                gcEntity.setId(id);
                gcEntity.setStartTime(startTime);
                gcEntity.setEndTime(endTime);
            }
            returnStr+=gcEntity.toString();
//            gcEntities.add(gcEntity);
        }
        return returnStr;
    }
}
