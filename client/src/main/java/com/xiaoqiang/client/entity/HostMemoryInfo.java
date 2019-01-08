package com.xiaoqiang.client.entity;

public class HostMemoryInfo{
    private long totalPhysicalMemorySize;
    private long freePhysicalMemorySize;
    private long totalSwapSpaceSize;

    private long freeSwapSpaceSize;

    public HostMemoryInfo(long totalPhysicalMemorySize, long freePhysicalMemorySize, long totalSwapSpaceSize, long freeSwapSpaceSize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
        this.freePhysicalMemorySize = freePhysicalMemorySize;
        this.totalSwapSpaceSize = totalSwapSpaceSize;
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }

    @Override
    public String toString() {
        return "HostMemoryInfo{" +
                "totalPhysicalMemorySize=" + totalPhysicalMemorySize +
                ", freePhysicalMemorySize=" + freePhysicalMemorySize +
                ", totalSwapSpaceSize=" + totalSwapSpaceSize +
                ", freeSwapSpaceSize=" + freeSwapSpaceSize +
                '}';
    }

    public long getTotalPhysicalMemorySize() {
        return totalPhysicalMemorySize;
    }

    public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }

    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public long getTotalSwapSpaceSize() {
        return totalSwapSpaceSize;
    }

    public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
        this.totalSwapSpaceSize = totalSwapSpaceSize;
    }

    public long getFreeSwapSpaceSize() {
        return freeSwapSpaceSize;
    }
    public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }

}
