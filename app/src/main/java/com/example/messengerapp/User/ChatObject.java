package com.example.messengerapp.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatObject implements Serializable {

    private String chatID;

    private ArrayList<UserObject> userObjectArrayList = new ArrayList<>();

    public ChatObject(String chatID){
        this.chatID = chatID;
    }

    public String getChatID(){
        return chatID;
    }

    public ArrayList<UserObject> getUserObjectArrayList(){
        return userObjectArrayList;
    }

    public void addUserToArrayList(UserObject mUser){
        userObjectArrayList.add(mUser);
    }

}
