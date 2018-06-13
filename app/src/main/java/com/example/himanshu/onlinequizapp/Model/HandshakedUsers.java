package com.example.himanshu.onlinequizapp.Model;

/**
 * Created by himanshu on 2/3/18.
 */

public class HandshakedUsers {
    private String sender;
    private String receiver;

    public HandshakedUsers() {
    }

    public HandshakedUsers(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
