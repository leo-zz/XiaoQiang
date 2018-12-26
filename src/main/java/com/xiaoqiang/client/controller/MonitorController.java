package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.configuration.XiaoQiangMonitorConfigBean;
import com.xiaoqiang.client.monitor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String getClassMonitor() {
        return xiaoQiangMonitorConfigBean.isClassloader() ? ClassMonitor.ClassLoadInfo().toString() : "未开启class监控。";
    }

    @RequestMapping(value = "cpu", method = RequestMethod.GET)
    public String getCPUMonitor() {
        return xiaoQiangMonitorConfigBean.isCpu() ? CPUMonitor.CPUInfo().toString() : "未开启CPU监控。";
    }

    @RequestMapping(value = "gc", method = RequestMethod.GET)
    public String getGCMonitor() {
        return xiaoQiangMonitorConfigBean.isGc() ? GCMonitor.GCInfo() : "未开启GC监控。";
    }

    @RequestMapping(value = "memory", method = RequestMethod.GET)
    public String getMemoryMonitor() {
        return xiaoQiangMonitorConfigBean.isMemory() ? MemoryMonitor.MemoryInfo().toString() : "未开启Memory监控";
    }

    @RequestMapping(value = "runtime", method = RequestMethod.GET)
    public String getRuntimeMonitor() {
        return xiaoQiangMonitorConfigBean.isRuntime() ? RuntimeMonitor.RuntimeInfo().toString() : "未开启runtime监控";
    }

    @RequestMapping(value = "thread", method = RequestMethod.GET)
    public String getThreadMonitor() {
        return xiaoQiangMonitorConfigBean.isThread() ? ThreadMonitor.ThreadInfo().toString() : "未开启thread监控";
    }
}
