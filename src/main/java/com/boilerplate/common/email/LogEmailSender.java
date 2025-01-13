package com.boilerplate.common.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Profile("local")
@Component
public class LogEmailSender implements EmailSender {

    @Override
    public void send(String from, String senderName, String to, String recipientName, String subject, String body, Boolean html) {
        logEmailDetails(from, senderName, to, recipientName, subject, body);
    }

    @Override
    public void asyncSend(String from, String senderName, String to, String recipientName, String subject, String body, Boolean html) {
        send(from, senderName, to, recipientName, subject, body, html);
    }

    private void logEmailDetails(String from, String senderName, String to, String recipientName, String subject, String body) {
        try {
            Path logPath = Path.of("logs", "email", subject + ".html");
            log.debug("EMAIL SENT! SEE {}", logPath.toString());
            log.debug("From: {}", from);
            log.debug("Sender Name: {}", senderName);
            log.debug("Recipient: {}", to);
            log.debug("Recipient Name: {}", recipientName);
            log.debug("Subject: {}", subject);
            Files.createDirectories(logPath.getParent());
            Files.writeString(logPath, utf8() + body);
        } catch (IOException e) {
            log.error("Failed to log email details", e);
        }
    }

    private String utf8() {
        return "<meta charset=\"utf-8\">";
    }
}