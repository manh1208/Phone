package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto;

import android.support.v4.app.Fragment;

/**
 * Created by ManhNV on 6/14/2016.
 */
public class Utilities {
    private int image;
    private String title;
    private String description;
    private Fragment fragment;

    public Utilities() {
    }

    public Utilities(int image, String title, String description,Fragment fragment) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.fragment = fragment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
