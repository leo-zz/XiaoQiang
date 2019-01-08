package com.xiaoqiang.client.entity;

import java.util.List;

public class RuntimeEntity {

    private String jvmName;
    private List<String> inputArguments;
    private long jvmStartTime;
    private String osName;
    private String compilerName;
    private String specVersion;
    private String specName;

    public RuntimeEntity(String jvmName, List<String> inputArguments, long startTime, String osName, String compilerName, String specVersion, String specName) {
        this.jvmName = jvmName;
        this.inputArguments = inputArguments;
        this.jvmStartTime = startTime;
        this.osName = osName;
        this.compilerName = compilerName;
        this.specVersion = specVersion;
        this.specName = specName;
    }

    @Override
    public String toString() {
        return "RuntimeEntity{" +
                "jvmName='" + jvmName + '\'' +
                ", inputArguments=" + inputArguments +
                ", jvmStartTime=" + jvmStartTime +
                ", osName='" + osName + '\'' +
                ", compilerName='" + compilerName + '\'' +
                ", specVersion='" + specVersion + '\'' +
                ", specName='" + specName + '\'' +
                '}';
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public List<String> getInputArguments() {
        return inputArguments;
    }

    public void setInputArguments(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }

    public long getJvmStartTime() {
        return jvmStartTime;
    }

    public void setJvmStartTime(long jvmStartTime) {
        this.jvmStartTime = jvmStartTime;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getCompilerName() {
        return compilerName;
    }

    public void setCompilerName(String compilerName) {
        this.compilerName = compilerName;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
