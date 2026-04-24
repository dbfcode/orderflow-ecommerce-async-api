package com.orderflow.ecommerce.messaging.consumer;

import com.orderflow.ecommerce.messaging.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    @RabbitListener(queues = "${orderflow.rabbitmq.queue-inventory}")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Inventory: processing order created orderId={} total={} items={}",
                event.orderId(), event.total(), event.items() != null ? event.items().size() : 0);
        // Reserve or debit stock by productId + quantity when inventory service is available

    }
}
