package com.example.smartspend.model;

public class Chat {
    boolean user;
    String content;

    public Chat(boolean b, String newResponse) {
        this.user = b;
        this.content = newResponse;
    }
}
