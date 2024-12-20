package com.boilerplate.common.email;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SpringEmailSender implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(
            String from,
            String senderName,
            String to,
            String recipientName,
            String subject,
            String body,
            Boolean html
    ) {
        try {
            log.info("메일 발송, 제목: {}, 발신자: {} 수신자: {}", subject, from, to);

            mailSender.send((MimeMessage message) -> {
                final MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(new InternetAddress(from, senderName));
                helper.setTo(new InternetAddress(to, recipientName));
                helper.setSubject(subject);
                helper.setText(body, html);
            });
        } catch (MailException e) {
            log.error("[메일 발송의 실패] {}. 제목: {}, 발신자: {} 수신자: {}", e.getMessage(), subject, from, to);
        }


    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void asyncSend(
            String from,
            String senderName,
            String to,
            String recipientName,
            String subject,
            String body,
            Boolean html
    ) {
        send(from, senderName, to, recipientName, subject, body, html);
    }
}
