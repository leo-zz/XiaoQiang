package com.xiaoqiang.client.monitor.entity;

import java.util.Arrays;

public class MemoryManager {

    private String name;
    private String[] memoryPoolNames;
    private boolean valid;

    public MemoryManager(String name, String[] memoryPoolNames, boolean valid) {
        this.name = name;
        this.memoryPoolNames = memoryPoolNames;
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "MemoryManager{" +
                "name='" + name + '\'' +
                ", memoryPoolNames=" + Arrays.toString(memoryPoolNames) +
                ", valid=" + valid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMemoryPoolNames() {
        return memoryPoolNames;
    }

    public void setMemoryPoolNames(String[] memoryPoolNames) {
        this.memoryPoolNames = memoryPoolNames;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
