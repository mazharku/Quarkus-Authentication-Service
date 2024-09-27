package org.acme.model.dto;

public class Message {
   public String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }
}
