package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.configuration.XiaoQiangMonitorConfigBean;
import com.xiaoqiang.client.entity.*;
import com.xiaoqiang.client.monitor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${xiaoqiang.monitor.path:/monitor}")
public class MonitorController {
    @Autowired
    private XiaoQiangMonitorConfigBean xiaoQiangMonitorConfigBean;


    @RequestMapping(value = "class", method = RequestMethod.GET)
    public HttpResult<ClassEntity> getClassMonitor() {
        return xiaoQiangMonitorConfigBean.isClazz() ? new HttpResult(true, ClassMonitor.ClassLoadInfo()) : new HttpResult(false);
    }

    @RequestMapping(value = "cpu", method = RequestMethod.GET)
    public HttpResult<CPUEntity> getCPUMonitor() {
        return xiaoQiangMonitorConfigBean.isCpu() ? new HttpResult(true, CPUMonitor.CPUInfo()) : new HttpResult(false);
    }

    @RequestMapping(value = "gc", method = RequestMethod.GET)
    public HttpResult<List<GCEntity>> getGCMonitor() {
        return xiaoQiangMonitorConfigBean.isGc() ? new HttpResult(true, GCMonitor.GCInfo()) : new HttpResult(false);//"未开启GC监控。";
    }

    @RequestMapping(value = "memory", method = RequestMethod.GET)
    public HttpResult<MemoryEntity> getMemoryMonitor() {
        return xiaoQiangMonitorConfigBean.isMemory() ? new HttpResult(true, MemoryMonitor.MemoryInfo()) : new HttpResult(false);//.toString() : "未开启Memory监控";
    }

    @RequestMapping(value = "runtime", method = RequestMethod.GET)
    public HttpResult<RuntimeEntity> getRuntimeMonitor() {
        return xiaoQiangMonitorConfigBean.isRuntime() ? new HttpResult(true, RuntimeMonitor.RuntimeInfo()) : new HttpResult(false);//.toString() : "未开启runtime监控";
    }

    @RequestMapping(value = "thread", method = RequestMethod.GET)
    public HttpResult<ThreadEntity> getThreadMonitor() {
        return xiaoQiangMonitorConfigBean.isThread() ? new HttpResult(true, ThreadMonitor.ThreadInfo()) : new HttpResult(false); //.toString() : "未开启thread监控";
    }
}
