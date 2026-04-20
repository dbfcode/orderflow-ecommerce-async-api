package com.orderflow.ecommerce.messaging.publisher;

import com.orderflow.ecommerce.messaging.config.RabbitMqProperties;
import com.orderflow.ecommerce.messaging.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, RabbitMqProperties rabbitMqProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqProperties = rabbitMqProperties;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getRoutingKeyOrderCreated(),
                event
        );
    }
}
