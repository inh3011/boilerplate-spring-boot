package com.boilerplate.domain.service.email;

import com.boilerplate.common.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Objects;

import static java.util.Locale.KOREAN;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Setter
    @Value("${spring.mail.username}")
    private String emailSender;

    @Setter
    @Value("${spring.mail.sender-name}")
    private String senderName;

    public void send(String to, String recipientName, String subject, ModelAndView modelAndView) {
        String body = templateEngine.process(Objects.requireNonNull(modelAndView.getViewName()), new Context(KOREAN, modelAndView.getModel()));
        mailSender.send(emailSender, senderName, to,  recipientName, subject, body, true);
    }

    public void asyncSend(String to, String recipientName, String subject,  ModelAndView modelAndView) {
        String body = templateEngine.process(Objects.requireNonNull(modelAndView.getViewName()), new Context(KOREAN, modelAndView.getModel()));
        mailSender.asyncSend(emailSender, senderName, to, recipientName, subject, body, true);
    }

    public void send(String to, String recipientName, EmailSubject subject, ModelAndView modelAndView) {
        String body = templateEngine.process(Objects.requireNonNull(modelAndView.getViewName()), new Context(KOREAN, modelAndView.getModel()));
        mailSender.send(emailSender, senderName, to, recipientName, subject.getSubject(), body, true);
    }

    public void asyncSend(String to, String recipientName, EmailSubject subject, ModelAndView modelAndView) {
        String body = templateEngine.process(Objects.requireNonNull(modelAndView.getViewName()), new Context(KOREAN, modelAndView.getModel()));
        mailSender.asyncSend(emailSender, senderName, to, recipientName, subject.getSubject(), body, true);
    }
}
