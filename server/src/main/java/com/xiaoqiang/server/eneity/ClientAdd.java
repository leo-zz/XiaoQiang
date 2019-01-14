package com.xiaoqiang.server.eneity;

public class ClientAdd {

    private  int port;
    private  String address;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientAdd( String address,int port) {
        this.address = address;
        this.port = port;
    }
}
