package com.example.messengerapp.User;

public class MessageObject {

    String messageID, senderID, message;

    public MessageObject(String messageID, String senderID, String message){
        this.messageID = messageID;
        this.senderID = senderID;
        this.message = message;
    }

    public String getMessageID(){
        return messageID;
    }

    public String getSenderID(){
        return senderID;
    }

    public String getMessage(){
        return message;
    }
}
