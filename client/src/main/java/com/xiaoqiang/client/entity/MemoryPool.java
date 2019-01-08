package com.xiaoqiang.client.entity;

import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.Arrays;

public class MemoryPool {

    private String name;
    private MemoryType type;
    private MemoryUsage usage;
    private MemoryUsage peakUsage;
    private String[] memoryManagerNames;
    private MemoryUsage lastGCCollectionUsage;

    public MemoryPool(MemoryUsage usage, MemoryType type, MemoryUsage peakUsage, String name, String[] memoryManagerNames, MemoryUsage lastGCCollectionUsage) {
        this.usage = usage;
        this.type = type;
        this.peakUsage = peakUsage;
        this.name = name;
        this.memoryManagerNames = memoryManagerNames;
        this.lastGCCollectionUsage = lastGCCollectionUsage;
    }

    @Override
    public String toString() {
        return "MemoryPool{" +
                "usage=" + usage +
                ", type=" + type +
                ", peakUsage=" + peakUsage +
                ", name='" + name + '\'' +
                ", memoryManagerNames=" + Arrays.toString(memoryManagerNames) +
                ", lastGCCollectionUsage=" + lastGCCollectionUsage +
                '}';
    }

    public MemoryUsage getUsage() {
        return usage;
    }

    public void setUsage(MemoryUsage usage) {
        this.usage = usage;
    }

    public MemoryType getType() {
        return type;
    }

    public void setType(MemoryType type) {
        this.type = type;
    }

    public MemoryUsage getPeakUsage() {
        return peakUsage;
    }

    public void setPeakUsage(MemoryUsage peakUsage) {
        this.peakUsage = peakUsage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMemoryManagerNames() {
        return memoryManagerNames;
    }

    public void setMemoryManagerNames(String[] memoryManagerNames) {
        this.memoryManagerNames = memoryManagerNames;
    }

    public MemoryUsage getLastGCCollectionUsage() {
        return lastGCCollectionUsage;
    }

    public void setLastGCCollectionUsage(MemoryUsage lastGCCollectionUsage) {
        this.lastGCCollectionUsage = lastGCCollectionUsage;
    }
}
