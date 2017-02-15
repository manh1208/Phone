package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManhNV on 5/30/2016.
 */
public class Contact implements Comparable<Contact>{

    private String id;
    private String name;
    private List<String> phoneNumber;
    private String photoUri;
    private List<String> emails;

    public Contact(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber.add(phoneNumber);
    }
    public Contact(String id, String name) {
        this.id = id;
        this.name = name;
        phoneNumber = new ArrayList<String>();
        emails= new ArrayList<String>();
    }
    public Contact() {
        phoneNumber = new ArrayList<String>();
        emails= new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.add(phoneNumber);
    }

    public void setEmail(String email){
        this.emails.add(email);
    }

    public List<String> getEmail(){
        return emails;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    @Override
    public int compareTo(Contact another) {
        return name.compareTo(another.getName());
    }
}
