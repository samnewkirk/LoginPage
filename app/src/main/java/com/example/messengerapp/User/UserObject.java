package com.example.messengerapp.User;

import java.io.Serializable;

public class UserObject implements Serializable {
    private String name, phone, uid, notificationKey;

    private Boolean selected = false;

    public UserObject(String name, String phone, String uid){
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public UserObject(String uid) {
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
    public String getNotificationKey() {
        return notificationKey;
    }
    public Boolean getSelected() {
        return selected;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }


}
