package com.example.ds2023_device.service;

import com.example.ds2023_device.entity.model.Device;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;


    public void sendMessage(Device device){
        LOGGER.info(String.format("Message sent -> %s", device));
        rabbitTemplate.convertAndSend(exchange, routingKey, convertObjectToJson(device));
    }


    private String convertObjectToJson(Device device) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(device);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting DeviceLimitDTO to JSON", e);
        }
    }
}

