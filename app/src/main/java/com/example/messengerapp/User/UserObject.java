package com.example.messengerapp.User;

public class UserObject {
    private String name, phone, uid;

    public UserObject(String name, String phone, String uid){
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
    public String getUid() {
        return uid;
    }

    public void setName(String name) { this.name = name; }


}
