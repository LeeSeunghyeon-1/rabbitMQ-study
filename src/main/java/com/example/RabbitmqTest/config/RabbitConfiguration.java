package com.example.RabbitmqTest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

// foobar.queue 큐를 생성
//    @Bean
//    class foobarQueue(): Queue {
//
//        return Queue("foobar.queue", false)
//    }
//
//    // foobar.direct.exchange 익스체인지를 생성
//    @Bean
//    class foobarDirectExchange(): DirectExchange {
//
//        return DirectExchange("foobar.direct.exchange")
//    }
//
//    // 라우팅을 맵핑한 바인딩을 생성
//    @Bean
//    class foobarBinding(foobarDirectExchange: DirectExchange, foobarQueue: Queue): Binding {
//
//        return BindingBuilder
//                .bind(foobarQueue)
//                .to(foobarDirectExchange)
//                .withQueueName()
//    }

}