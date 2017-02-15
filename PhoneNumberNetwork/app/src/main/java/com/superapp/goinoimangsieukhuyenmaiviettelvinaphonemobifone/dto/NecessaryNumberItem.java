package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class NecessaryNumberItem {
    private int id;
    private String name;
    private String phone;
    private String price;
    private String unit;


    public NecessaryNumberItem(int id, String name, String phone, String price, String unit) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
