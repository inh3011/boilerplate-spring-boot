package com.boilerplate.domain.service.email;

import com.boilerplate.common.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailSender mailSender;

    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService(mailSender, templateEngine);
        emailService.setEmailSender("test@example.com");
        emailService.setSenderName("Test Sender");
    }

    @Test
    void send() {
        // Given
        String to = "recipient@example.com";
        String recipientName = "Recipient Name";
        String subject = "Test Subject";
        ModelAndView modelAndView = new ModelAndView("email/test_mail.html");
        modelAndView.addObject("recipientName", "테스터");
        modelAndView.addObject("message", "메일 테스트 입니다.");
        given(templateEngine.process(anyString(), any(Context.class))).willReturn("Email Body");

        // When
        emailService.send(to, recipientName, subject, modelAndView);

        // Then
        verify(mailSender).send(
                eq("test@example.com"),
                eq("Test Sender"),
                eq(to),
                eq(recipientName),
                eq(subject),
                eq("Email Body"),
                eq(true)
        );
    }

    @Test
    void asyncSend() {
    }
}