package com.orderflow.ecommerce.messaging.consumer;

import com.orderflow.ecommerce.messaging.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    @RabbitListener(queues = "${orderflow.rabbitmq.queue-email}")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Email: preparing notification for orderId={} status={}", event.orderId(), event.status());
        // Enfileirar envio de e-mail quando houver serviço de notificação
    }
}
