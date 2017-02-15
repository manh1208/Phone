package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class NecessaryNumberGroup {
    private String name;
    private String icon;
    private List<NecessaryNumberItem> data;

    public NecessaryNumberGroup(){
        data = new ArrayList<NecessaryNumberItem>();
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NecessaryNumberItem> getData() {
        return data;
    }

    public void setData(NecessaryNumberItem data) {
        if (this.data==null){
            this.data = new ArrayList<NecessaryNumberItem>();
        }
        this.data.add(data);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
