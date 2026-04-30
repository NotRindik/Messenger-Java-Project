package com.example.messengermaxsat.model;

import java.io.Serializable;

public class TextMessage implements Serializable {
    private String sender;
    private String content;

    public TextMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
}