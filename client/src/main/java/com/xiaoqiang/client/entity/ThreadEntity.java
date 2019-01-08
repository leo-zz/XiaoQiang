package com.xiaoqiang.client.entity;

public class ThreadEntity {

    int threadCount;
    int peakThreadCount;
    long totalStartedThreadCount;
    int deadlockedThreadsNum;
    int monitorDeadlockedThreadsNum;

    public ThreadEntity(int threadCount, int peakThreadCount, long totalStartedThreadCount, int deadlockedThreadsNum, int monitorDeadlockedThreadsNum) {
        this.threadCount = threadCount;
        this.peakThreadCount = peakThreadCount;
        this.totalStartedThreadCount = totalStartedThreadCount;
        this.deadlockedThreadsNum = deadlockedThreadsNum;
        this.monitorDeadlockedThreadsNum = monitorDeadlockedThreadsNum;
    }

    @Override
    public String toString() {
        return "ThreadEntity{" +
                "threadCount=" + threadCount +
                ", peakThreadCount=" + peakThreadCount +
                ", totalStartedThreadCount=" + totalStartedThreadCount +
                ", deadlockedThreadsNum=" + deadlockedThreadsNum +
                ", monitorDeadlockedThreadsNum=" + monitorDeadlockedThreadsNum +
                '}';
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    public void setPeakThreadCount(int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    public void setTotalStartedThreadCount(long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
    }

    public int getDeadlockedThreadsNum() {
        return deadlockedThreadsNum;
    }

    public void setDeadlockedThreadsNum(int deadlockedThreadsNum) {
        this.deadlockedThreadsNum = deadlockedThreadsNum;
    }

    public int getMonitorDeadlockedThreadsNum() {
        return monitorDeadlockedThreadsNum;
    }

    public void setMonitorDeadlockedThreadsNum(int monitorDeadlockedThreadsNum) {
        this.monitorDeadlockedThreadsNum = monitorDeadlockedThreadsNum;
    }
}
