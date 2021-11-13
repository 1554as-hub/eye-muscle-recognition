package com.eyescloud.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean("file_handler_exchange")
    public Exchange KitchenExchange(){
        return ExchangeBuilder.topicExchange("file_handler_exchange").durable(true).build();
    }

    @Bean("file_handler_queue")
    public Queue KitchenQueue(){
        return QueueBuilder.durable("file_handler_queue").build();
    }

    @Bean
    public Binding KitchenBinding(@Qualifier("file_handler_queue") Queue queue ,
                                  @Qualifier("file_handler_exchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("file.#").noargs();
    }

}
