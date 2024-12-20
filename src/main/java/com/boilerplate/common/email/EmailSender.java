package com.boilerplate.common.email;

public interface EmailSender {
    void send(String from, String senderName, String to, String recipientName, String subject, String body, Boolean html);
    void asyncSend(String from, String senderName, String to, String recipientName, String subject, String body, Boolean html);
}
