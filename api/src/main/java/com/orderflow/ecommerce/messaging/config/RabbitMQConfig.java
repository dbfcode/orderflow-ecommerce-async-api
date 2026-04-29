package com.orderflow.ecommerce.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public TopicExchange orderEventsExchange(RabbitMqProperties props) {
        return new TopicExchange(props.getExchangeName(), true, false);
    }

    @Bean
    public Queue inventoryQueue(RabbitMqProperties props) {
        return new Queue(props.getQueueInventory(), true);
    }

    @Bean
    public Queue emailQueue(RabbitMqProperties props) {
        return new Queue(props.getQueueEmail(), true);
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, TopicExchange orderEventsExchange, RabbitMqProperties props) {
        return BindingBuilder.bind(inventoryQueue)
                .to(orderEventsExchange)
                .with(props.getRoutingKeyOrderCreated());
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange orderEventsExchange, RabbitMqProperties props) {
        return BindingBuilder.bind(emailQueue)
                .to(orderEventsExchange)
                .with(props.getRoutingKeyOrderCreated());
    }
}
