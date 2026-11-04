package com.tasktracker.task_tracker_email_sender.listener;

import com.tasktracker.task_tracker_email_sender.model.EmailTask;
import com.tasktracker.task_tracker_email_sender.service.EmailSendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailTaskListener {
    private final EmailSendingService emailSendingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "EMAIL_SENDING_TASKS", groupId = "email-sender-group")
    public void handle(String payload) {
        log.info("Email task received: {}", payload);
        try {
            EmailTask emailTask = objectMapper.readValue(payload, EmailTask.class);
            emailSendingService.send(emailTask);
            log.info("Email sent: recipient={}", emailTask.recipient());
        } catch (MailException | jakarta.mail.MessagingException e) {
            log.error("Failed to send email: {}", e.getMessage());
        } catch (RuntimeException e) {
            log.error("Failed to parse email task: {}", e.getMessage());
        }
    }
}
