package com.fs.ballerinachatapp;

/**
 * Created by Dinithi on 7/22/2017.
 */

public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}