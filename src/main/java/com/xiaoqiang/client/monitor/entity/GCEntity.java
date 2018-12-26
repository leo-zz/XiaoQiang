package com.xiaoqiang.client.monitor.entity;

import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class GCEntity {
    long collectionCount;
    long collectionTime;
    String[] memoryPoolNames;
    String name;
    Map<String, MemoryUsage> memoryUsageBeforeGc;
    Map<String, MemoryUsage> memoryUsageAfterGc;
    long duration;
    long id;
    long startTime;
    long endTime;

    @Override
    public String toString() {
        String str = "";
        str += "GCEntity{" +
                "collectionCount=" + collectionCount +
                ", collectionTime=" + collectionTime +
                ", memoryPoolNames=" + Arrays.toString(memoryPoolNames) +
                ", name='" + name + '\'' +
                ", memoryUsageBeforeGc=";
        if (memoryUsageBeforeGc != null) {
            for (String s : memoryUsageBeforeGc.keySet()) {
                MemoryUsage memoryUsage = memoryUsageBeforeGc.get(s);
                str += s + ":" + memoryUsage.toString();
            }
        } else {
            str += "null";
        }
        if (memoryUsageAfterGc != null) {
            str += ", memoryUsageAfterGc=";
            for (String s : memoryUsageAfterGc.keySet()) {
                MemoryUsage memoryUsage = memoryUsageBeforeGc.get(s);
                str += s + ":" + memoryUsage.toString();
            }
            ;
        } else {
            str += "null";
        }
        str += ", duration=" + duration +
                ", id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
        return str;
    }

    public long getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(long collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String[] getMemoryPoolNames() {
        return memoryPoolNames;
    }

    public void setMemoryPoolNames(String[] memoryPoolNames) {
        this.memoryPoolNames = memoryPoolNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, MemoryUsage> getMemoryUsageBeforeGc() {
        return memoryUsageBeforeGc;
    }

    public void setMemoryUsageBeforeGc(Map<String, MemoryUsage> memoryUsageBeforeGc) {
        this.memoryUsageBeforeGc = memoryUsageBeforeGc;
    }

    public Map<String, MemoryUsage> getMemoryUsageAfterGc() {
        return memoryUsageAfterGc;
    }

    public void setMemoryUsageAfterGc(Map<String, MemoryUsage> memoryUsageAfterGc) {
        this.memoryUsageAfterGc = memoryUsageAfterGc;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
