package com.example.messenger;

public class Message {

    private String text;
    private String senderId;
    private String receiverId;


    public Message(String receiverId, String senderId, String text) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.text = text;
    }

    public Message(){}

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

}
