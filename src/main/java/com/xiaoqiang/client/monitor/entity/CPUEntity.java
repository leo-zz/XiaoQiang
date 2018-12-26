package com.xiaoqiang.client.monitor.entity;

public class CPUEntity {

    private int availableProcessors;
    private double jvmCpuLoad;
    private double systemCpuLoad;
    private double systemLoadAverage;

    @Override
    public String toString() {
        return "CPUEntity{" +
                "availableProcessors=" + availableProcessors +
                ", jvmCpuLoad=" + jvmCpuLoad +
                ", systemCpuLoad=" + systemCpuLoad +
                ", systemLoadAverage=" + systemLoadAverage +
                '}';
    }

    public CPUEntity(int availableProcessors, double jvmCpuLoad, double systemCpuLoad, double systemLoadAverage) {
        this.availableProcessors = availableProcessors;
        this.jvmCpuLoad = jvmCpuLoad;
        this.systemCpuLoad = systemCpuLoad;
        this.systemLoadAverage = systemLoadAverage;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public double getJvmCpuLoad() {
        return jvmCpuLoad;
    }

    public void setJvmCpuLoad(double jvmCpuLoad) {
        this.jvmCpuLoad = jvmCpuLoad;
    }

    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public double getSystemLoadAverage() {
        return systemLoadAverage;
    }

    public void setSystemLoadAverage(double systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
    }
}
