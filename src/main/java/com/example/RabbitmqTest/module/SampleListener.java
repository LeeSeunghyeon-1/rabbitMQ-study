package com.example.RabbitmqTest.module;

import com.example.RabbitmqTest.entity.Receive;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class SampleListener {

    private static final Logger log = LoggerFactory.getLogger(SampleListener.class);

//    @RabbitListener(queues = "seoultel.service.jungjin_report")
//    public void reciveMessage(final Message message) {
//        log.info(message.toString());
//    }

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
        // save 같은 느낌

        recvChannel.basicConsume("seoultel.service.jungjin_report", false, new Consumer(recvChannel));
        System.out.println("Consumer 등록");

    }

    class Consumer extends DefaultConsumer {

        Gson gson1 = new Gson();

        Gson gson2 = new GsonBuilder().create();
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();


//        public class receiveDB {
//
////            private String newId;
////            private String newResult;
////            private String newRequestDateTime;
////            private String newProcessingDateTime;
//
//            public receiveDB (String newId, String newResult, String newRequestDateTime, String newProcessingDateTime){
////            super();
//                this.newId = newId;
//                this.newResult = newResult;
//                this.newRequestDateTime = newRequestDateTime;
//                this.newProcessingDateTime = newProcessingDateTime;
//            }
//
//            // 0 배열 저장해서 다시 저장하기
////            @Override
////            public String toString(){
////                return "receiveDB [id=" + newId + ", result =" + newResult + ", requestDateTime ="  + newRequestDateTime + ", processingDateTime = " + newProcessingDateTime + " ] ";
////            }
//
//
//            @Override
//            public String toString() {
//                return "receiveDB{" +
//                        "newId='" + newId + '\'' +
//                        ", newResult='" + newResult + '\'' +
//                        ", newRequestDateTime='" + newRequestDateTime + '\'' +
//                        ", newProcessingDateTime='" + newProcessingDateTime + '\'' +
//                        '}';
//            }
//        }



        public void JsonToObject(){

        }

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

            if(message != null && !message.isEmpty()){
                log.info("message = {}", message);
            }else{
                System.out.println("ERROR : The message is null or empty !!");
            }

            Gson gson = new Gson();
            Receive receive = gson.fromJson(message, Receive.class);
            log.info("야아ㅏㅏ ㅏ여기 봐ㅑ라여기야 확인해라ㅏㅏㅏ");
            log.info("receive = {}", receive);
            log.info("requestId = {}",receive.getRequestId());
            
        }
    }
}

//TODO : 테이블에서 전달받은 ID 가 같은 행은 삭제, 새로운 테이블 추가해서 전달받은 데이터 DB에 입력