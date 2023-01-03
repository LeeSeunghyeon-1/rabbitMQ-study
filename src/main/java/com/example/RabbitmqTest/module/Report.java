
//package com.example.RabbitmqTest.module;
//
//import com.example.RabbitmqTest.entity.ExcelMessage;
//import com.example.RabbitmqTest.repository.ExcelRepository;
//import lombok.Getter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.GsonBuilder;
//
//
//@Getter
//@Component
//public class Report {
//
//    class reportFatcher extends Thread {
//        @Override
//        public void run(){
//            rabbitTemplate.receiveAndConvert("seoultel.service.jungjin_report");
//        }
//    }
//
//}
