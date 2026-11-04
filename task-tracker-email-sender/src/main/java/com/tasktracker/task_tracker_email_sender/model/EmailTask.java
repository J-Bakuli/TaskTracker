package com.tasktracker.task_tracker_email_sender.model;

public record EmailTask(
        String recipient,
        String subject,
        String text
) {
}
