package com.example.RabbitmqTest.module;

import com.example.RabbitmqTest.entity.ExcelMessage;
import com.example.RabbitmqTest.repository.ExcelRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;


//rabbitMQ sender 위해 선언
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;




@Getter
@Component
public class DbFetcher {

//    var factory = new ConnectionFactory();
//      factory.setHost("192.168.50.19");
//      factory.setUsername("arreo");
//      factory.setPassword("egarreo01");
//      try (Connection connection = factory.newConnection();
//    Channel channel = connection.createChannel()) {
//        channel.queueDeclare("seoultel.service.jungjin", true, false, false, null);
//        String message = "Hello jungjin!";
//        channel.basicPublish("service", "seoultel.service.jungjin", null, message.getBytes(StandardCharsets.UTF_8));
//        System.out.println(" [x] Sent '" + message + "'");
//    }

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    protected Logger log = LoggerFactory.getLogger("DbFetcher");

    @Autowired
    private ExcelRepository excelRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Fetcher fetcher;

    private String name;

    private Long report;

    /*public DbFetcher() {
        log.info("DbFetcher가 등록됨");
        new Fetcher().start(); // start() 는 Thread Class 안에있는 run()을 실행시킴
    }*/



    @PostConstruct
    public void DbFetcher(){
        log.info("DbFetcher가 등록됨");
        this.fetcher = new Fetcher();// start() 는 Thread Class 안에있는 run()을 실행시킴

        this.fetcher.stopSignal = false;
        this.fetcher.start();
    }

//    public void stopMessageProcess(){
//        getFetcher().stopSignal();
//    }


    class Fetcher extends Thread{

        private boolean stopSignal = true;



        public void updateFlag (ExcelMessage e) {
            e.setFlag("1");

            ExcelMessage updateFlag = excelRepository.save(e);

            log.info("updated message => {}", updateFlag.toString());


        }

            @Override
            public void run() {

                        while(!stopSignal){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                }
//                log.info("Thread 가 동작 함");
//                // DB에서 가져오는 동작을 구현



                //발송이 안된것들
                    List<ExcelMessage> excelList =  excelRepository.findByFlag("0");

    //                if(excelList.size() == 0){
    //                    continue;
    //                }
                    for (int i = 0; i < excelList.size(); i++){

                        ExcelMessage excelMessage = excelList.get(i);
                        //안된것들을 발송해줌


                        rabbitTemplate.convertAndSend("service", "seoultel.service.jungjin", excelMessage);
                        //발송을 했으니 Flag을 1로 바꿔준다.
    //                    ExcelMessage excelList = excelRepository.findByFlag("0");
                        updateFlag(excelMessage);
                    }rabbitTemplate.receiveAndConvert("seoultel.service.jungjin_report");
               }
            }



        }


//
//        public void stopSignal(){
//            this.stopSignal = true;
//        }

    }



//    @Component
//    static
//    class Sender extends Thread {
//       // Sender send = new Sender();
//
//    }

//
//    @PostConstruct
//    public void init(){
//        log.info("DbFetcher가 등록됨");
//    }


//}

