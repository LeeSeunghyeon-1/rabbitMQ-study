package com.example.RabbitmqTest.module;

import com.example.RabbitmqTest.entity.ExcelMessage;

import com.example.RabbitmqTest.repository.ExcelRepository;
import com.example.RabbitmqTest.repository.ReceiveRepository;
import com.rabbitmq.client.*;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import com.example.RabbitmqTest.entity.ReceiveMessage;

import javax.annotation.PostConstruct;
import javax.swing.plaf.PanelUI;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Getter
@Setter
@Component
public class SampleListener {

    private static final Logger log = LoggerFactory.getLogger(SampleListener.class);

    @Autowired
    private ExcelRepository excelRepository;

    @Autowired
    private ReceiveRepository receiveRepository;

    @Autowired
    private ReceiveRepository receiveMessage;



    @PostConstruct
    public void recv() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.50.19");
        //factory.setPort(5672);    -> Default
        factory.setUsername("arreo");
        factory.setPassword("egarreo01");

        Connection recvConnection = factory.newConnection();
        Channel recvChannel = recvConnection.createChannel();

//        recvChannel.queueDeclare("seoultel.service.jungjin_report", true, false, false, null);
//        System.out.println(" [SH] Waiting for messages. To exit press CTRL + C");
        // save ?????? ??????

        recvChannel.basicConsume("seoultel.service.jungjin_report", false, new Consumer(recvChannel));
        System.out.println("Consumer ??????");


    }


//    private NewFetcher newFetcher;

//    @PostConstruct
//    public void NewFetcher(){
//        log.info("????????? DB??? ?????????");
//        this.newFetcher = new NewFetcher();
//        this.newFetcher.start();
//    }



//    class NewFetcher extends Thread {
//        public void saveMessage (ReceiveMessage r){
//
////            saveAll();
//            ReceiveMessage saveMessage = receiveRepository.save(r);
//
//            log.info("NewDb update => {}", saveMessage.toString());
//        }
//
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//
//            }
//                saveMessage(receiveMessage);
//                log.info("receiveMessage?????? = > {}", receiveMessage);
//
//        }
//    }


    class Consumer extends DefaultConsumer {
        String name;


        public Consumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
            super.handleShutdownSignal(consumerTag, sig);

        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//            super.handleDelivery(consumerTag, envelope, properties, body);
            System.out.println("SendReportThread running - searching queue");
            String message = new String(body);

            if (message != null && !message.isEmpty()) {
                log.info("message = {}", message);
            } else {
                System.out.println("ERROR : The message is null or empty !!");
            }

            Gson gson = new Gson();
            ReceiveMessage receive = gson.fromJson(message, ReceiveMessage.class);
            log.info("receive = {}", receive);
            log.info("requestId = {}", receive.getRequestId());

            //TODO : some Validation..{}


            // Id??? find
//            ExcelMessage excelMessage = excelRepository.findByRequestId(receive.getRequestId());


            ExcelMessage excelMessage = excelRepository.findByRequestIdAndFlag(receive.getRequestId(), "1");

            if(excelMessage != null){
                //TODO : some ????????????
                log.info("????????? Id ????????? = {}",excelMessage);
                excelRepository.deleteById(excelMessage.getRequestId());



                receiveRepository.save(receive);

                log.info("receiveRepository = {}", receiveRepository);



            }else{
                //TODO : ???????????? or ????????? ??????
                log.info("?????? Id??? ????????????.");


            }


            //TODO ?????? ?????? ??? receive ?????? ?????? ???????????? ????????? id ??? ???????????? ??????????????? ????????????


        }


    }
}


//TODO : ??????????????? ???????????? ID ??? ?????? ?????? ??????, ????????? ????????? ???????????? ???????????? ????????? DB??? ??????