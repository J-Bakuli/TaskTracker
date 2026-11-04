package com.tasktracker.task_tracker_email_sender.service;

import com.tasktracker.task_tracker_email_sender.model.EmailTask;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendingService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String from;

    public void send(EmailTask task) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, "UTF-8");
        if (from != null && !from.isBlank()) {
            helper.setFrom(from);
        }
        helper.setTo(task.recipient());
        helper.setSubject(task.subject());
        helper.setText(task.text(), false);
        mailSender.send(message);
    }
}
