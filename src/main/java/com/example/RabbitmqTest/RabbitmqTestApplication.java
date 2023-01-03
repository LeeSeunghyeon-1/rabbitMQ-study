package com.example.RabbitmqTest;

import com.example.RabbitmqTest.entity.ExcelMessage;
import com.example.RabbitmqTest.module.DbFetcher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;


@SpringBootApplication
public class RabbitmqTestApplication{

	public static void main(String[] args) {

		SpringApplication.run(RabbitmqTestApplication.class, args);

	}

}
