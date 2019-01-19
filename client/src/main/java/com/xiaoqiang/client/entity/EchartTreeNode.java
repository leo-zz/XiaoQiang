package com.xiaoqiang.client.entity;

public class EchartTreeNode {
    private String name;
    private String value;
    private EchartTreeNode[] children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EchartTreeNode[] getChildren() {
        return children;
    }

    public void setChildren(EchartTreeNode[] children) {
        this.children = children;
    }

    public EchartTreeNode(String name) {
        this.name = name;
    }

    public EchartTreeNode() {
    }
}
