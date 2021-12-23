package ru.akulin.output;

import ru.akulin.entities.HistoricalMessage;

import java.util.List;

public class History extends Response {

    private List<HistoricalMessage> messages;

    public History(List<HistoricalMessage> messages) {
        super(true);
        this.messages = messages;
    }

    public List<HistoricalMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<HistoricalMessage> messages) {
        this.messages = messages;
    }
}
