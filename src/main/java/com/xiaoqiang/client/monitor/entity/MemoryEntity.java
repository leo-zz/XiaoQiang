package com.xiaoqiang.client.monitor.entity;

import java.util.List;

public class MemoryEntity {
    private JVMMemory jvmMemory;
    private List<MemoryPool> memoryPools;
    private List<MemoryManager> memoryManagers;
    private HostMemoryInfo hostMemoryInfo;

    @Override
    public String toString() {
        return "MemoryEntity{" +
                "jvmMemory=" + jvmMemory +
                ", memoryPools=" + memoryPools +
                ", memoryManagers=" + memoryManagers +
                ", hostMemoryInfo=" + hostMemoryInfo +
                '}';
    }

    public JVMMemory getJvmMemory() {
        return jvmMemory;
    }

    public void setJvmMemory(JVMMemory jvmMemory) {
        this.jvmMemory = jvmMemory;
    }

    public List<MemoryPool> getMemoryPools() {
        return memoryPools;
    }

    public void setMemoryPools(List<MemoryPool> memoryPools) {
        this.memoryPools = memoryPools;
    }

    public List<MemoryManager> getMemoryManagers() {
        return memoryManagers;
    }

    public void setMemoryManagers(List<MemoryManager> memoryManagers) {
        this.memoryManagers = memoryManagers;
    }

    public HostMemoryInfo getHostMemoryInfo() {
        return hostMemoryInfo;
    }

    public void setHostMemoryInfo(HostMemoryInfo hostMemoryInfo) {
        this.hostMemoryInfo = hostMemoryInfo;
    }
}
