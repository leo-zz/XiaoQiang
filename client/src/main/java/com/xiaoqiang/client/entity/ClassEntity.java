package com.xiaoqiang.client.entity;

public class ClassEntity {
    private int loadedClassCount;
    private long totalLoadedClassCount;
    private long unloadedClassCount;
    private String bootClassPath;
    private String classPath;

    @Override
    public String toString() {
        return "ClassEntity{" +
                "loadedClassCount=" + loadedClassCount +
                ", totalLoadedClassCount=" + totalLoadedClassCount +
                ", unloadedClassCount=" + unloadedClassCount +
                ", bootClassPath='" + bootClassPath + '\'' +
                ", classPath='" + classPath + '\'' +
                '}';
    }

    public ClassEntity(int loadedClassCount, long totalLoadedClassCount, long unloadedClassCount, String bootClassPath, String classPath) {
        this.loadedClassCount = loadedClassCount;
        this.totalLoadedClassCount = totalLoadedClassCount;
        this.unloadedClassCount = unloadedClassCount;
        this.bootClassPath = bootClassPath;
        this.classPath = classPath;
    }

    public ClassEntity() {
    }

    public int getLoadedClassCount() {
        return loadedClassCount;
    }

    public void setLoadedClassCount(int loadedClassCount) {
        this.loadedClassCount = loadedClassCount;
    }

    public long getTotalLoadedClassCount() {
        return totalLoadedClassCount;
    }

    public void setTotalLoadedClassCount(long totalLoadedClassCount) {
        this.totalLoadedClassCount = totalLoadedClassCount;
    }

    public long getUnloadedClassCount() {
        return unloadedClassCount;
    }

    public void setUnloadedClassCount(long unloadedClassCount) {
        this.unloadedClassCount = unloadedClassCount;
    }

    public String getBootClassPath() {
        return bootClassPath;
    }

    public void setBootClassPath(String bootClassPath) {
        this.bootClassPath = bootClassPath;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}

