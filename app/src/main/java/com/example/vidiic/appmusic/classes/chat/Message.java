package com.example.vidiic.appmusic.classes.chat;

import com.example.vidiic.appmusic.classes.User;

public class Message {

    String message;
    User sender;
    long createdAt;


    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
