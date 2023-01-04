package com.example.RabbitmqTest.module;

import com.example.RabbitmqTest.entity.ExcelMessage;
import com.example.RabbitmqTest.entity.Receive;
import com.example.RabbitmqTest.repository.ExcelRepository;
import com.rabbitmq.client.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.example.RabbitmqTest.entity.ReceiveMessage;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Getter
@Setter
@Component
public class SampleListener {

    private static final Logger log = LoggerFactory.getLogger(SampleListener.class);

    @Autowired
    private ExcelRepository excelRepository;

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

    @Autowired
    private ReceiveMessage receiveMessage;

//    class NewFetcher extends Thread {
//        public void updateMessage (ReceiveMessage r){
//            ReceiveMessage updateMessage =
//        }
//    }

    //TODO DbFetcher 보면서 새로 들어온 메시지 넣는것만 하면 됨 위에 주석처리해둔거 보면 됨

    class Consumer extends DefaultConsumer {
        String name;

//        Gson gson1 = new Gson();
//
//        Gson gson2 = new GsonBuilder().create();
//        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        //private Receive receive;

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


//        public void JsonToObject(){
//
//        }

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
            Receive receive = gson.fromJson(message, Receive.class);
            log.info("receive = {}", receive);
            log.info("requestId = {}", receive.getRequestId());

            //TODO : some Validation..{}


            // Id로 find
//            ExcelMessage excelMessage = excelRepository.findByRequestId(receive.getRequestId());

            for (int j = 0; j < receive.getRequestId().length(); j++) {

              ExcelMessage excelMessage = excelRepository.findByRequestIdAndFlag(receive.getRequestId(), "1");

            if(excelMessage != null){
                //TODO : some 정상로직
                log.info("삭제한 Id 확인용 = {}",excelMessage);
                excelRepository.deleteById(excelMessage.getRequestId());

            }else{
                //TODO : 예외처리 or 버리기 등등
                log.info("중복 Id가 아닙니다.");


            }

            }




//            ExcelMessage excelMessage = new ExcelMessage();
//            excelMessage.getRequestId();

//            String jsonMap = String.valueOf(receive);
//            Map<String, Object> stringObjectMapwq = new HashMap<>();
//            Map<String, Object> stringObjectMap = gson.fromJson(jsonMap, Map.class);
//            Receive receive1 = gson.fromJson(jsonMap, Receive.class);
//
//            for(int i=0; i<2; i++){
//                jsonArr[i] = message[i]
//
//            }
//            log.info("jsonMap = {}", jsonMap);
//            if (receive.getRequestId()) {

//            }

//            if (excelMessage.getRequestId() == receive.getRequestId(i)) {
//
//            }
//            for (int i = 0; i < excelMessage.getRequestId().length(); i++) {
//
//                for (int j = 0; j < receive.getRequestId().length(); j++) {
//                    if (excelMessage.getRequestId() == receive.getRequestId()) {
//
//                    }
//                }
//            }
//            excelMessage.iloc[[0],[0]];


            //TODO 배열 생성 후 receive 넣고 배열 길이만큼 돌려서 id 값 비교해서 테이블에서 삭제하기


        }


    }
}


//TODO : 테이블에서 전달받은 ID 가 같은 행은 삭제, 새로운 테이블 추가해서 전달받은 데이터 DB에 입력