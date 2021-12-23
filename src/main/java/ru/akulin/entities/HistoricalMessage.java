package ru.akulin.entities;

public class HistoricalMessage {

    private long timestamp;
    private String sender;
    private String text;

    public HistoricalMessage(long timestamp, String sender, String text) {
        this.timestamp = timestamp;
        this.sender = sender;
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
