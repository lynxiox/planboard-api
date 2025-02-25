package com.example.planboardapi.kafka.service;

import com.example.planboardapi.kafka.dto.EmailSendingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.planboardapi.kafka.config.KafkaTopicConfig.emailSendingTask;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, EmailSendingDto> kafkaTemplate;

    public void sendRegisterEmailMessage(EmailSendingDto emailSendingDto) {
        kafkaTemplate.send(emailSendingTask, emailSendingDto);
    }

}