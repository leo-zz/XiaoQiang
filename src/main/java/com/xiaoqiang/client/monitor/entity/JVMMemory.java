package com.xiaoqiang.client.monitor.entity;

public class JVMMemory{
    private long heapUsedMemory;
    private long heapCommittedMemory;
    private long nonHeapUsedMemory;
    private long nonHeapCommittedMemory;
    private long committedVirtualMemorySize;

    @Override
    public String toString() {
        return "JVMMemory{" +
                "heapUsedMemory=" + heapUsedMemory +
                ", heapCommittedMemory=" + heapCommittedMemory +
                ", nonHeapUsedMemory=" + nonHeapUsedMemory +
                ", nonHeapCommittedMemory=" + nonHeapCommittedMemory +
                ", committedVirtualMemorySize=" + committedVirtualMemorySize +
                '}';
    }

    public JVMMemory(long heapUsedMemory, long heapCommittedMemory, long nonHeapUsedMemory, long nonHeapCommittedMemory, long committedVirtualMemorySize) {
        this.heapUsedMemory = heapUsedMemory;
        this.heapCommittedMemory = heapCommittedMemory;
        this.nonHeapUsedMemory = nonHeapUsedMemory;
        this.nonHeapCommittedMemory = nonHeapCommittedMemory;
        this.committedVirtualMemorySize = committedVirtualMemorySize;
    }

    public long getHeapUsedMemory() {
        return heapUsedMemory;
    }

    public void setHeapUsedMemory(long heapUsedMemory) {
        this.heapUsedMemory = heapUsedMemory;
    }

    public long getHeapCommittedMemory() {
        return heapCommittedMemory;
    }

    public void setHeapCommittedMemory(long heapCommittedMemory) {
        this.heapCommittedMemory = heapCommittedMemory;
    }

    public long getNonHeapUsedMemory() {
        return nonHeapUsedMemory;
    }

    public void setNonHeapUsedMemory(long nonHeapUsedMemory) {
        this.nonHeapUsedMemory = nonHeapUsedMemory;
    }

    public long getNonHeapCommittedMemory() {
        return nonHeapCommittedMemory;
    }

    public void setNonHeapCommittedMemory(long nonHeapCommittedMemory) {
        this.nonHeapCommittedMemory = nonHeapCommittedMemory;
    }

    public long getCommittedVirtualMemorySize() {
        return committedVirtualMemorySize;
    }

    public void setCommittedVirtualMemorySize(long committedVirtualMemorySize) {
        this.committedVirtualMemorySize = committedVirtualMemorySize;
    }
}