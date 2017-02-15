package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto;

import java.util.List;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class NecessaryNumberCity {
    private int id;
    private String city;
    private List<NecessaryNumberGroup> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<NecessaryNumberGroup> getData() {
        return data;
    }

    public void setData(List<NecessaryNumberGroup> data) {
        this.data = data;
    }
}
