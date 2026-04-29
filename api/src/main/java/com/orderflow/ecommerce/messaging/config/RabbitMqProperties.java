package com.orderflow.ecommerce.messaging.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orderflow.rabbitmq")
public class RabbitMqProperties {

    private String exchangeName = "order.events";
    private String queueInventory = "order.inventory";
    private String queueEmail = "order.email";
    private String routingKeyOrderCreated = "order.created";

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getQueueInventory() {
        return queueInventory;
    }

    public void setQueueInventory(String queueInventory) {
        this.queueInventory = queueInventory;
    }

    public String getQueueEmail() {
        return queueEmail;
    }

    public void setQueueEmail(String queueEmail) {
        this.queueEmail = queueEmail;
    }

    public String getRoutingKeyOrderCreated() {
        return routingKeyOrderCreated;
    }

    public void setRoutingKeyOrderCreated(String routingKeyOrderCreated) {
        this.routingKeyOrderCreated = routingKeyOrderCreated;
    }
}
