package com.project.stephencao.treeview.bean;

import com.project.stephencao.treeview.annotation.TreeNodeId;
import com.project.stephencao.treeview.annotation.TreeNodeLabel;
import com.project.stephencao.treeview.annotation.TreeNodePId;

public class FileBean {
    @TreeNodeId(type = Integer.class)
    private int id;
    @TreeNodePId
    private int pId;
    @TreeNodeLabel
    private String label;
    private String desc;

    public FileBean(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
