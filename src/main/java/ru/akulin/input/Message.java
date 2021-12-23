package ru.akulin.input;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Message {

    private String text;

    @JsonCreator // Какой-то косяк, без этой аннотации конструктор с одним параметром не работает
    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
