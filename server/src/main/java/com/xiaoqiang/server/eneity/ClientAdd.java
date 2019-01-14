package com.xiaoqiang.server.eneity;

public class ClientAdd {

    private  int port;
    private  String instanceName;
    private  String address;
    private  Long lastConnTime;
    private  boolean activeFlag;

    public ClientAdd(String address,int port, String instanceName) {
        this.port = port;
        this.instanceName = instanceName;
        this.address = address;
        this.activeFlag=true;
        this.lastConnTime=-1l;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLastConnTime() {
        return lastConnTime;
    }

    public void setLastConnTime(Long lastConnTime) {
        this.lastConnTime = lastConnTime;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return "ClientAdd{" +
                "port=" + port +
                ", instanceName='" + instanceName + '\'' +
                ", address='" + address + '\'' +
                ", lastConnTime=" + lastConnTime +
                ", activeFlag=" + activeFlag +
                '}';
    }
}
