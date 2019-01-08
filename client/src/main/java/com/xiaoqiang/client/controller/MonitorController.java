package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.configuration.XiaoQiangMonitorConfigBean;
import com.xiaoqiang.client.entity.*;
import com.xiaoqiang.client.monitor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("${xiaoqiang.monitor.path:/monitor}")
@RequestMapping("/monitor")
public class MonitorController {

//    //    @Value("xiaoqiang.client.classloader")
//    private boolean isOpenClassloaderMonitor;
//
//    //    @Value("xiaoqiang.client.cpu")
//    private boolean isOpenCPUMonitor;
//
//    //    @Value("xiaoqiang.client.gc")
//    private boolean isOpenGCMonitor;
//
//    //    @Value("xiaoqiang.client.memory")
//    private boolean isOpenMemoryMonitor;
//
//    //    @Value("xiaoqiang.client.runtime")
//    private boolean isOpenRunTimeMonitor;
//
//    //    @Value("xiaoqiang.client.thread")
//    private boolean isOpenThreadMonitor;

    @Autowired
    private XiaoQiangMonitorConfigBean xiaoQiangMonitorConfigBean;


    @RequestMapping(value = "class", method = RequestMethod.GET)
        public ClassEntity getClassMonitor() {
        return xiaoQiangMonitorConfigBean.isClassloader() ? ClassMonitor.ClassLoadInfo() : null;
    }

    @RequestMapping(value = "cpu", method = RequestMethod.GET)
    public CPUEntity getCPUMonitor() {
        return xiaoQiangMonitorConfigBean.isCpu() ? CPUMonitor.CPUInfo() : null;
    }

    @RequestMapping(value = "gc", method = RequestMethod.GET)
    public List<GCEntity> getGCMonitor() {
        return xiaoQiangMonitorConfigBean.isGc() ? GCMonitor.GCInfo() : null;//"未开启GC监控。";
    }

    @RequestMapping(value = "memory", method = RequestMethod.GET)
    public MemoryEntity getMemoryMonitor() {
        return xiaoQiangMonitorConfigBean.isMemory() ? MemoryMonitor.MemoryInfo():null ;//.toString() : "未开启Memory监控";
    }

    @RequestMapping(value = "runtime", method = RequestMethod.GET)
    public RuntimeEntity getRuntimeMonitor() {
        return xiaoQiangMonitorConfigBean.isRuntime() ? RuntimeMonitor.RuntimeInfo():null;//.toString() : "未开启runtime监控";
    }

    @RequestMapping(value = "thread", method = RequestMethod.GET)
    public ThreadEntity getThreadMonitor() {
        return xiaoQiangMonitorConfigBean.isThread() ? ThreadMonitor.ThreadInfo():null; //.toString() : "未开启thread监控";
    }
}
